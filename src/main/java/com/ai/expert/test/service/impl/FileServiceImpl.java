package com.ai.expert.test.service.impl;

import ai.expert.nlapi.v1.message.RequestDocument;

import ai.expert.nlapi.v1.model.Knowledge;

import com.ai.expert.test.dao.*;
import com.ai.expert.test.entity.FileEntity;
import com.ai.expert.test.entity.ResponseDoc;
import com.ai.expert.test.entity.response.*;
import com.ai.expert.test.service.FileService;

import com.ai.expert.test.utils.FileReader;

import com.ai.expert.test.utils.Globals;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    FileRepository fileRepository;
    @Autowired
    ResponseDocRepository responseDocRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    KnowledgeRepository knowledgeRepository;
    @Autowired
    TokensRepository tokensRepository;
    @Autowired
    PhrasesRepository phrasesRepository;
    @Autowired
    SentencesRepository sentencesRepository;
    @Autowired
    ParagraphsRepository paragraphsRepository;
    @Autowired
    TopicsRepository topicsRepository;
    @Autowired
    MainSentencesRepository mainSentencesRepository;
    @Autowired
    MainPhrasesRepository mainPhrasesRepository;
    @Autowired
    MainLemmasRepository mainLemmasRepository;
    @Autowired
    MainSynconsRepository mainSynconsRepository;
    @Autowired
    EntitiesRepository entitiesRepository;
    @Autowired
    DepencyRepository depencyRepository;
    @Autowired
    VsynconRepository vsynconRepository;
    @Autowired
    AtomRepository atomRepository;
    @Autowired
    PropertyRepository propertyRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Value("${expertai.username}")
    private String username;

    @Value("${expertai.password}")
    private String password;

    @Value("${expert.inputFolder}")
    private String folder;

    @Value("${expertai.cat.url}")
    private String urlCat;

    @Value("${expertai.url}")
    private String urlAnalysis;

    @Value("${expertai.auth.url}")
    private String authUrl;


    @Override
    public DataTablesOutput findAll(DataTablesInput input) {
        return fileRepository.findAll(input);
    }

    @Override
    public void storeFileDb() {

        List<FileEntity> listFile = (List<FileEntity>) fileRepository.findAll();

        if (listFile.isEmpty()) {

            FileReader fileReader = new FileReader();
            fileReader.readFiles(folder);

            for (File file : fileReader.getFiles()) {
                FileEntity fileEntity = new FileEntity();
                long size = file.length();

                try {
                    fileEntity.setTitle(file.getName())
                            .setFile(Files.readAllBytes(Paths.get(file.getPath())))
                            .setSize(size / 1024); //kb
                    fileRepository.save(fileEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("Files loaded");
        } else {
            LOGGER.info("The files already exist in the database");
        }
    }

    @Override
    public void deleteFileEntityById(Long id) {
        fileRepository.deleteFileEntityById(id);
    }

    @Override
    public void processFile(Long id) throws Exception {

        Optional<FileEntity> fileObj = fileRepository.findById(id);

        if (fileObj.isPresent()) {
            LOGGER.info("CATEGORIZATION --START--");
            String txtCat = new String(fileObj.get().getFile());

            if (txtCat.length() > 10000) {
                txtCat = txtCat.substring(0, 10000);
            }

            HttpEntity<String> requestEntity = new HttpEntity<>(RequestDocument.of(txtCat).toJSON(), getRequestHeaderBearer());

            ResponseDoc doc = restTemplate.postForObject(urlCat, requestEntity, ResponseDoc.class);

            if (doc != null) {
                this.saveCategory(doc, fileObj.get().getTitle() + Globals.ANALYSIS_TYPE_CAT + Globals.OUTPUT_EXTENSION);
            } else {
                throw new Exception();
            }

            LOGGER.info("CATEGORIZATION --END--");
            LOGGER.info("EXTRACTION --START--");

            ResponseDoc docExtraction = restTemplate.postForObject(urlAnalysis, requestEntity, ResponseDoc.class);

            if (docExtraction != null) {
                this.saveAnalysis(docExtraction, fileObj.get().getTitle() + Globals.ANALYSIS_TYPE_EXT_FULL + Globals.OUTPUT_EXTENSION);
            } else {
                throw new Exception();
            }

        }

        LOGGER.info("EXTRACTION --END--");
    }

    public void saveCategory(ResponseDoc responseDoc, String fileName) throws IOException {

        DataModel document = new DataModel();
        document.setContent(responseDoc.getData().getContent());
        document.setLanguage(responseDoc.getData().getLanguage().code());
        document.setVersion(responseDoc.getData().getVersion());
        document.setFileName(fileName);
        responseDocRepository.save(document);

        for (ai.expert.nlapi.v1.model.Category c : responseDoc.getData().getCategories()) {

            Category cat = new Category();
            cat.setScore(c.getScore());
            cat.setNamespace(c.getNamespace());
            cat.setLabel(c.getLabel());
            cat.setFrequency(c.getFrequency());
            cat.setWinner(c.getWinner());
            cat.setHierarchy(c.getHierarchy());
            cat.setResponseDoc(document);
            categoryRepository.save(cat);

            for (ai.expert.nlapi.v1.model.Position p : c.getPositions()) {

                Position position = new Position();
                position.setCategory(cat);
                position.setStart(p.getStart());
                position.setEnd(p.getEnd());
                positionRepository.save(position);
            }
        }
    }

    public void saveAnalysis(ResponseDoc responseDoc, String fileName) throws IOException {

        LOGGER.info("ANALISYS START");

        DataModel document = new DataModel();
        document.setContent(responseDoc.getData().getContent());
        document.setLanguage(responseDoc.getData().getLanguage().code());
        document.setVersion(responseDoc.getData().getVersion());
        document.setFileName(fileName);
        responseDocRepository.save(document);

        for (ai.expert.nlapi.v1.model.Knowledge k : responseDoc.getData().getKnowledge()) {

            com.ai.expert.test.entity.response.Knowledge know = new com.ai.expert.test.entity.response.Knowledge();
            know.setLabel(k.getLabel());
            know.setResponseDoc(document);
            know.setSyncon(k.getSyncon());
            knowledgeRepository.save(know);

            if (CollectionUtils.isNotEmpty(k.getProperties())) {
                for (ai.expert.nlapi.v1.model.Property p : k.getProperties()) {

                    Property property = new Property();
                    property.setKnowledge(know);
                    property.setType(p.getType());
                    property.setValue(p.getValue());

                    propertyRepository.save(property);
                }
            }
        }

        for (ai.expert.nlapi.v1.model.Token t : responseDoc.getData().getTokens()) {

            Token tok = new Token();
            tok.setResponseDoc(document);
            tok.setSyncon(t.getSyncon());
            tok.setLemma(t.getLemma());
            tok.setMorphology(t.getMorphology());
            tok.setParagraph(t.getParagraph());
            tok.setSentence(t.getSentence());
            tok.setPhrase(tok.getPhrase());
            tok.setStart(t.getStart());
            tok.setEnd(t.getEnd());
            tok.setPos(t.getPos().getDescription());
            tok.setType(t.getType().getDescription());


            if (t.getVsyn() != null) {
                Vsyncon vsyncon = new Vsyncon();
                vsyncon.setParent(t.getVsyn().getParent());
                vsyncon.setToken(tok);
                tok.setVsyn(vsyncon);
            }

            if (CollectionUtils.isNotEmpty(t.getAtoms())) {
                for (ai.expert.nlapi.v1.model.Atom at : t.getAtoms()) {

                    Atom atom = new Atom();
                    atom.setLemma(at.getLemma());
                    atom.setType(at.getType());
                    atom.setStart(at.getStart());
                    atom.setEnd(at.getEnd());
                    atom.setToken(tok);
                    atomRepository.save(atom);
                }
            }

            Dependency dependency = new Dependency();
            dependency.setIdDepency(t.getDependency().getId());
            dependency.setHead(t.getDependency().getHead());
            dependency.setLabel(t.getDependency().getLabel());
            dependency.setToken(tok);
            tok.setDependency(dependency);

            tokensRepository.save(tok);

        }

        for (ai.expert.nlapi.v1.model.Phrase ph : responseDoc.getData().getPhrases()) {

            Phrase phrase = new Phrase();
            phrase.setTokens(ph.getTokens());
            phrase.setType(ph.getType().getDescription());
            phrase.setStart(ph.getStart());
            phrase.setEnd(ph.getEnd());
            phrase.setResponseDoc(document);
            phrasesRepository.save(phrase);
        }

        for (ai.expert.nlapi.v1.model.Sentence s : responseDoc.getData().getSentences()) {

            Sentence sentence = new Sentence();
            sentence.setPhrases(s.getPhrases());
            sentence.setStart(s.getStart());
            sentence.setEnd(s.getEnd());
            sentence.setResponseDoc(document);
            sentencesRepository.save(sentence);

        }

        for (ai.expert.nlapi.v1.model.Paragraph p : responseDoc.getData().getParagraphs()) {

            Paragraph paragraph = new Paragraph();
            paragraph.setSentences(p.getSentences());
            paragraph.setStart(p.getStart());
            paragraph.setEnd(p.getEnd());
            paragraph.setResponseDoc(document);

            paragraphsRepository.save(paragraph);
        }


        for (ai.expert.nlapi.v1.model.Topic t : responseDoc.getData().getTopics()) {

            Topic topic = new Topic();
            topic.setIdTopic(t.getId());
            topic.setLabel(t.getLabel());
            topic.setScore(t.getScore());
            topic.setWinner(t.getWinner());
            topic.setResponseDoc(document);

            topicsRepository.save(topic);
        }

        for (ai.expert.nlapi.v1.model.MainSentence ms : responseDoc.getData().getMainSentences()) {

            MainSentence mainSentence = new MainSentence();
            mainSentence.setValue(ms.getValue());
            mainSentence.setScore(ms.getScore());
            mainSentence.setStart(ms.getStart());
            mainSentence.setEnd(ms.getEnd());
            mainSentence.setResponseDoc(document);

            mainSentencesRepository.save(mainSentence);
        }

        for (ai.expert.nlapi.v1.model.MainPhrase mp : responseDoc.getData().getMainPhrases()) {

            MainPhrase mainPhrase = new MainPhrase();
            mainPhrase.setValue(mp.getValue());
            mainPhrase.setScore(mp.getScore());
            mainPhrase.setResponseDoc(document);
            mainPhrasesRepository.save(mainPhrase);

            if (CollectionUtils.isNotEmpty(mp.getPositions())) {
                for (ai.expert.nlapi.v1.model.Position p : mp.getPositions()) {

                    Position position = new Position();
                    position.setStart(p.getStart());
                    position.setEnd(p.getEnd());
                    position.setMainPhrase(mainPhrase);

                    positionRepository.save(position);
                }

            }

            for (ai.expert.nlapi.v1.model.MainLemma ml : responseDoc.getData().getMainLemmas()) {

                MainLemma mainLemma = new MainLemma();
                mainLemma.setValue(ml.getValue());
                mainLemma.setScore(ml.getScore());
                mainLemma.setResponseDoc(document);
                mainLemmasRepository.save(mainLemma);

                if (CollectionUtils.isNotEmpty(ml.getPositions())) {
                    for (ai.expert.nlapi.v1.model.Position p : ml.getPositions()) {

                        Position position = new Position();
                        position.setStart(p.getStart());
                        position.setEnd(p.getEnd());
                        position.setMainLemma(mainLemma);

                        positionRepository.save(position);
                    }
                }
            }

            for (ai.expert.nlapi.v1.model.MainSyncon ms : responseDoc.getData().getMainSyncons()) {

                MainSyncon mainSyncon = new MainSyncon();
                mainSyncon.setSyncon(ms.getSyncon());
                mainSyncon.setScore(ms.getScore());
                mainSyncon.setResponseDoc(document);
                mainSynconsRepository.save(mainSyncon);

                if (CollectionUtils.isNotEmpty(ms.getPositions())) {
                    for (ai.expert.nlapi.v1.model.Position p : ms.getPositions()) {

                        Position position = new Position();
                        position.setStart(p.getStart());
                        position.setEnd(p.getEnd());
                        position.setMainSyncon(mainSyncon);

                        positionRepository.save(position);
                    }
                }
            }

            for (ai.expert.nlapi.v1.model.Entity e : responseDoc.getData().getEntities()) {

                Entity entity = new Entity();
                entity.setSyncon(e.getSyncon());
                entity.setType(e.getType().getDescription());
                entity.setLemma(e.getLemma());
                entity.setResponseDoc(document);
                entitiesRepository.save(entity);

                if (CollectionUtils.isNotEmpty(e.getPositions())) {
                    for (ai.expert.nlapi.v1.model.Position p : e.getPositions()) {

                        Position position = new Position();
                        position.setStart(p.getStart());
                        position.setEnd(p.getEnd());
                        position.setEntity(entity);

                        positionRepository.save(position);
                    }
                }
            }

            LOGGER.info("ANALISYS END");

        }
    }

    public String retrieveToken() throws IOException {
        Response response;
        RequestBody body;
        OkHttpClient httpClient = new OkHttpClient();
        String token = "";

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        body = RequestBody.create(Globals.CONTENT, json.toString());

        Request request = new Request.Builder().url(authUrl).post(body).build();

        LOGGER.info("\t\tCalling WS to retrieve the TOKEN");
        LOGGER.info("\t\tWS-host: " + authUrl);
        response = httpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            LOGGER.error("\t\tError calling WS for retrieve the TOKEN");
            LOGGER.info("\t\tTOKEN NOT RETRIEVED");
        } else {
            token = response.body().string();
            response.close();
            LOGGER.info("\t\tTOKEN RETRIEVED");
        }

        LOGGER.info("\t\tCall executed");

        return token;
    }

    public HttpHeaders getRequestHeaderBearer() throws IOException {
        String retrieveToken = this.retrieveToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + retrieveToken);

        return headers;

    }


}
