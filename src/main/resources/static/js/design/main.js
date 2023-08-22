let xPos = 0;
let numImage = 1;

gsap.timeline()
    .set('.l-stage', { rotationY:180, cursor:'grab' }) //set initial rotationY so the parallax jump happens off screen
    .set('.l-img',  { // apply transform rotations to each image
      rotateY: (i)=> i*-36,
      transformOrigin: '50% 50% 500px',
      z: -500,
      backgroundImage:(i)=>'url(https://picsum.photos/id/'+(i+32)+'/600/400/)',
      backgroundPosition:(i)=>getBgPos(i),
      backfaceVisibility:'hidden'
    })
    .from('.l-img', {
      duration:1.5,
      y:200,
      opacity:0,
      stagger:0.1,
      ease:'expo'
    })
    .add(()=>{
      $('.l-img').on('mouseenter', (e)=>{
        let current = e.currentTarget;
        gsap.to('.l-img', {opacity:(i,t)=>(t==current)? 1:0.5, ease:'power3'})
      })
      $('.l-img').on('mouseleave', (e)=>{
        gsap.to('.l-img', {opacity:1, ease:'power2.inOut'})
      })
    }, '-=0.5')

$(window).on('mousedown touchstart', dragStart);
$(window).on('mouseup touchend', dragEnd);


function dragStart(e){
  if (e.touches) e.clientX = e.touches[0].clientX;
  xPos = Math.round(e.clientX);
  gsap.set('.l-stage', {cursor:'grabbing'})
  $(window).on('mousemove touchmove', drag);
}


function drag(e){
  if (e.touches) e.clientX = e.touches[0].clientX;

  gsap.to('.l-stage', {
    rotationY: '-=' +( (Math.round(e.clientX)-xPos)%360 ),
    onUpdate:()=>{ gsap.set('.l-img', { backgroundPosition:(i)=>getBgPos(i) }) }
  });

  xPos = Math.round(e.clientX);
}


function dragEnd(e){
  $(window).off('mousemove touchmove', drag);
  gsap.set('.l-stage', {cursor:'grab'});
}


function getBgPos(i){ //returns the background-position string to create parallax movement in each image
  return ( 100-gsap.utils.wrap(0,360,gsap.getProperty('.l-stage', 'rotationY')-180-i*36)/360*500 )+'px 0px';
}




