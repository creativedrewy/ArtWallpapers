import p5 from "p5";

function loadSketch(sketchText: string, width: string, height: string) {
    //TODO:
    //Parse width & height into numbers
    //Parse sketch code & add our configuration stuff to it
}

function resumeSketch() {
    //TODO: Remove p5 instance
}

function pauseSketch() {
    //TODO: Add p5 instance
}

const testScript = `
    let rectX = 0;
    let fr = 30; //starting FPS
    let clr;
    
    function setup() {
      createCanvas(360, 510);
    
      background(200);
      clr = color(255, 0, 0);
    
      frameRate(30);
    }
    
    function draw() {
      background(200);
      rectX += 1; // Move Rectangle
    
      if (rectX >= width) {
        // If you go off screen.
        if (fr === 30) {
          clr = color(0, 0, 255);
          fr = 10;
          //frameRate(fr);
        } else {
          clr = color(255, 0, 0);
          fr = 30;
          //frameRate(fr);
        }
        rectX = 0;
      }
    
      fill(clr);
      rect(rectX, height / 2, 20, 20);
    }
`

window.onload = () => {
    let userScript: HTMLScriptElement
    let p5Inst: p5

    const firstButton = document.getElementById("addButton")
    firstButton.onclick = () => {
        userScript = document.createElement('script')
        userScript.textContent = testScript
        document.body.appendChild(userScript);

        p5Inst = new p5(undefined, undefined);
    }

    const removeButton = document.getElementById("removeButton")
    removeButton.onclick = () => {
        let item = document.body.getElementsByClassName("p5Canvas")[0]
        item.remove()
        userScript.remove()
        p5Inst.remove()
    }
}