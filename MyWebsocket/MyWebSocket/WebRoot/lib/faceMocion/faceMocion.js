$.fn.extend({faceMocion: function(opciones) {
	var count=0;
	var faceMocion=this;
	var NombreSelector="Selector";		
	var DescripcionFace="--";
	defaults = { emociones:[
    {"emocion":"amo","TextoEmocion":"Lo amo"},
    {"emocion":"molesto","TextoEmocion":"Me molesta"},
    {"emocion":"asusta","TextoEmocion":"Me asusta"},
    {"emocion":"divierte","TextoEmocion":"Me divierte"},
    {"emocion":"gusta","TextoEmocion":"Me gusta"},
    {"emocion":"triste","TextoEmocion":"Me entristece"},
    {"emocion":"asombro","TextoEmocion":"Me asombra"},
    {"emocion":"alegre","TextoEmocion":"Me alegra"}
    ]};
	var opciones = $.extend({}, defaults, opciones);
    
       $(faceMocion).each(function( index ) {
           var UnicoID = Date.now();
           $(this).attr("class",$(faceMocion).attr("class")+" "+UnicoID);
           var EstadoInicial="alegre";
           if($(this).val()!=""){
               EstadoInicial=$(this).val(); 
           }else{
               $(this).val('alegre');
           }
           DescripcionFace=EstadoInicial;
           ElementoIniciar='';
           ElementoIniciar=ElementoIniciar+'<div dato-descripcion="'+DescripcionFace+'" ';
           ElementoIniciar=ElementoIniciar+'id-referencia="'+UnicoID;
           ElementoIniciar=ElementoIniciar+'"  class="'+NombreSelector;
           ElementoIniciar=ElementoIniciar+' selectorFace '+EstadoInicial+'"></div>';
           $(this).before(ElementoIniciar);
    });
    
    
    $(document).ready(function() {
        BarraEmociones='<div class="faceMocion">';
        $.each(opciones.emociones, function(index,emo ){
           BarraEmociones=BarraEmociones+'<div dato-descripcion="'+emo.TextoEmocion;    
           BarraEmociones=BarraEmociones+'" class="'+emo.emocion+'"></div>';
        });
          BarraEmociones=BarraEmociones+'</div>';
        $(document.body).append(BarraEmociones);
        $('.faceMocion div').hover(function(){
            var title = $(this).attr('dato-descripcion');
            $(this).data('tipText', title).removeAttr('dato-descripcion');
            $('<p class="MensajeTexto"></p>').text(title).appendTo('body').fadeIn('slow');
	    },function() {
            $(this).attr('dato-descripcion', $(this).data('tipText'));
            $('.MensajeTexto').remove();
        }).mousemove(function(e) {
            var RatonX = e.pageX - 20;var RatonY = e.pageY - 60;
            $('.MensajeTexto').css({ top: RatonY, left: RatonX })
        });
	});
    
    $( '.'+NombreSelector ).hover(function(e) {
			SelectorEmocion= $(this);
            var RatonX = e.pageX - 20;var RatonY = e.pageY - 60;
            $( ".faceMocion" ).css({ top: RatonY, left: RatonX });
            $( ".faceMocion" ).show();   
            count=0;
            $( ".faceMocion div" ).click(function() { //facebookEmoji	.faceMocion div
            	SelectorEmocion.attr("class",NombreSelector+" selectorFace  "+$(this).attr('class'));
            
                ElInputSeleccionado=SelectorEmocion.attr("id-referencia");
                $("."+ElInputSeleccionado).val($(this).attr('class'));
                if(count==0){
                	var img="<div class='Selector selectorFace  "+$(this).attr('class')+
            		/*"' id-referencia='"+ElInputSeleccionado+*/
            		"' dato-descripcion='"+$(this).attr('class')+"'></div>";
                	var time=new Date();
            		var time2=new Date(parseInt(time.getTime())).toLocaleString();
            		var imgMessage = "<li><div class='message-data'><span class='message-data-name' style='font-size:20px'>"+
            				"我</span>"+
            				" <span class='message-data-time'>"+time2+"</span></div>"+
            				"<div class='message my-message' style='height:80px;width=80px;'>"+img+"</div></li>";
                	
                	$("#talk").append(imgMessage);
                	websocket.send(""+$(this).attr('class'));
                	count=-100;
                	return;
                }
                	    
			});
            
	});
    $(document).mouseup(function (e){  $( ".faceMocion" ).hide();});
      $(faceMocion).hide();
      
}
});