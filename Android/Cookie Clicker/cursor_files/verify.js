setTimeout(function(){
    'use strict';
    try{
        var stringifyFunc = null;
		if(window.JSON){
			stringifyFunc = window.JSON.stringify;
		} else {
			if(window.parent && window.parent.JSON){
				stringifyFunc = window.parent.JSON.stringify;
			}
		}
		if(!stringifyFunc){
			return;
		}
        var msg = {
            action: 'notifyBrandShieldAdEntityInformation',
            bsAdEntityInformation: {
                brandShieldId:'7119903c77d94f4089b603a48c2992cf',
                comparisonItems:[{name : 'cmp', value : 2317506},{name : 'plmt', value : 2317517}]
            }
        };
        var msgString = stringifyFunc(msg);
        var bst2tWin = null;

        var findAndSendMessage = function() {
            if (!bst2tWin) {
                var frame = document.getElementById('bst2t_60317249735');
                if (frame) {
                    bst2tWin = frame.contentWindow;
                }
            }

            if (bst2tWin) {
                bst2tWin.postMessage(msgString, '*');
            }
        };

        findAndSendMessage();
        setTimeout(findAndSendMessage, 50);
        setTimeout(findAndSendMessage, 500);
    } catch(err){}
}, 10);


try{__tagObject_callback_60317249735({ImpressionID:"7119903c77d94f4089b603a48c2992cf", ServerPublicDns:"tps607.doubleverify.com"});}catch(e){}
try{$dvbs.pubSub.publish('BeforeDecisionRender', "7119903c77d94f4089b603a48c2992cf");}catch(e){}
try{__verify_callback_60317249735({
ResultID:2,
Passback:"",
AdWidth:160,
AdHeight:600});}catch(e){}
try{$dvbs.pubSub.publish('AfterDecisionRender', "7119903c77d94f4089b603a48c2992cf");}catch(e){}
