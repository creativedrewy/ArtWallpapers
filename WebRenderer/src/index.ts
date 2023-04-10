import p5 from "p5";

let canvasWidth: Number
let canvasHeight: Number

let scriptSrc: string
let userScript: HTMLScriptElement
let p5Inst: p5

function loadSketch(sketchText: string, width: string, height: string) {
    canvasWidth = Number(width);
    canvasHeight = Number(height);
    scriptSrc = sketchText;

    //Parse sketch code & add our configuration stuff to it
    resumeSketch()
}

function resumeSketch() {
    userScript = document.createElement('script')
    userScript.textContent = scriptSrc
    document.body.appendChild(userScript);

    p5Inst = new p5(undefined, undefined);
}

function pauseSketch() {
    let item = document.body.getElementsByClassName("p5Canvas")[0]
    item.remove()
    userScript.remove()
    p5Inst.remove()
}

const testScript = `
    let rectX = 0;
    let fr = 30; //starting FPS
    let clr;
    
    function setup() {
      createCanvas(400, 400);
    
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
    const addButton = document.getElementById("addButton")
    addButton.onclick = () => {
        loadSketch(testScript, "400", "400");
    }

    const removeButton = document.getElementById("removeButton")
    removeButton.onclick = () => {
        pauseSketch()
    }
}