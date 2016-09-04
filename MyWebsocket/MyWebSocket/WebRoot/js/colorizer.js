/**
 * colorizer Beta version
 * https://github.com/miladdavoodi/colorizer
 */

(function () {
    (function ($) {

        var fun_methods, MediaNode, analyser, splitter, sourcemedia, ThisBG;
        var context = new AudioContext();

        fun_methods = {
            setupAudio: function () {

                MediaNode = context.createScriptProcessor(2048, 1, 1);
                MediaNode.connect(context.destination);

                analyser = context.createAnalyser();
                analyser.smoothingTimeConstant = 0.3;
                analyser.fftSize = 1024;

                sourcemedia = context.createBufferSource();
                splitter = context.createChannelSplitter();

                sourcemedia.connect(splitter);
                splitter.connect(analyser, 0, 0);
                analyser.connect(MediaNode);
                sourcemedia.connect(context.destination);


            },
            loadSound: function (url) {

                var request = new XMLHttpRequest();
                request.open('GET', url, true);
                request.responseType = 'arraybuffer';
                request.onload = function () {

                    context.decodeAudioData(request.response, function (buffer) {

                        sourcemedia.buffer = buffer;
                        sourcemedia.start(0);

                    }, fun_methods.onError);
                };
                request.send();
            },
            onError: function (e) {
                console.log(e);
            }

        };

        return $.extend({

            colorizer: function (body, arguments_options) {

                var optionslist = {
                    'file': null,
                    'echolizer': null,
                    'shadow': null
                };

                ThisBG = $(body);

                optionslist.body = body;
                options = $.extend(optionslist, arguments_options);

                fun_methods.setupAudio();
                fun_methods.loadSound(optionslist.file);

                MediaNode.onaudioprocess = function () {
                    var array = new Uint8Array(analyser.frequencyBinCount);
                    analyser.getByteFrequencyData(array);

                    ThisBG.css('background', 'rgba(' + array[0] + ',' + (array[1] / 2) + ',' + array[2] + ',0.' + array[0] + ')');
                    if (optionslist.shadow != null) $(optionslist.shadow).css('box-shadow', (array[0] / 5) + 'px ' + (array[0] / 5) + 'px 10px 0 rgba(0, 0, 0, 0.2)');
                    if (optionslist.echolizer != null) $(optionslist.echolizer).css('width', array[0] + 'px');

                }

            }
        });
    })(jQuery);

}).call();
