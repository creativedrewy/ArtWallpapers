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
  rect(rectX, 40, 20, 20);
}
`

window.onload = () => {
    let userScript: HTMLScriptElement
    let p5Script: HTMLScriptElement

    const firstButton = document.getElementById("addButton")
    firstButton.onclick = () => {

        userScript = document.createElement('script')
        userScript.textContent = testScript

        const p5Path = "https://cdn.jsdelivr.net/npm/p5@1.6.0/lib/p5.js"
        p5Script = document.createElement('script');
        p5Script.setAttribute('src', p5Path);

        document.body.appendChild(userScript);
        document.body.appendChild(p5Script);
    }

    const removeButton = document.getElementById("removeButton")
    removeButton.onclick = () => {
        let item = document.body.getElementsByClassName("p5Canvas")[0]
        item.remove()
        userScript.remove()
        p5Script.remove()
    }
}