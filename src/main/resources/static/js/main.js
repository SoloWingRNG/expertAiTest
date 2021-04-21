import '/vendor/jquery/jquery-3.4.1.js';
import '/vendor/dataTables/js/jquery.dataTables.js';
import '/vendor/bootstrap-notify/bootstrap-notify.min.js';
import '/vendor/bootstrap/js/bootstrap.js';
import '/vendor/dataTables/js/dataTables.bootstrap.js';
import '/vendor/jquery-serializeobject/jquery.serialize-object.js';
import '/js/common.js';
import '/js/home.js'
import '/vendor/fancybox/jquery.fancybox.js';
import '/vendor/fancybox/jquery.fancybox.pack.js';
import '/vendor/fancybox/jquery.mousewheel.pack.js';




function loadCss(href){
	let head  = document.getElementsByTagName('head')[0];
    let link  = document.createElement('link');
    link.rel  = 'stylesheet';
    link.type = 'text/css';
    link.href = href;
    link.media = 'all';
    head.appendChild(link);
}

loadCss('/vendor/bootstrap/css/bootstrap.css');
loadCss('/vendor/bootstrap/css/bootstrap-theme.css');
loadCss('/vendor/dataTables/css/dataTables.bootstrap.css');
loadCss('/vendor/fontawesome/css/fontawesome.min.css');
loadCss('/vendor/fancybox/jquery.fancybox.css');
loadCss('/css/css.css');