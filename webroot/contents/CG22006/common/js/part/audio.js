var audio = new Audio();
audio.src='';


function setAudio(_src){
  if(audio.src!='') audio.pause();
  audio.src='../common/audio/'+_src+'.mp3';
  audio.load();
  audio.volume = mainVideo.videoDOM.volume;
  audio.play();
}
