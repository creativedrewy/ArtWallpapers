export {};

import p5 from "p5";
import {parseScript, Program, Syntax} from "esprima";
import {generate} from "escodegen";
import {CallExpression, ExpressionStatement, Identifier} from "estree";

let canvasWidth: Number
let canvasHeight: Number

let scriptSrc: string
let userScript: HTMLScriptElement
let p5Inst: p5

window.testMe = () => {
    // setTimeout(() => {
        loadSketch(testScript, "540", "1110");
    // }, 2000)
}

export function testMe() {
    setTimeout(() => {
        loadSketch(testScript, "540", "1110");
    }, 2000)
}

export function loadSketch(sketchText: string, width: string, height: string) {
    canvasWidth = Number(width);
    canvasHeight = Number(height);

    //Parse sketch code & add our configuration stuff to it
    scriptSrc = sanitizeSetup(sketchText, width, height);

    resumeSketch();
}

/**
 * Remove any existing canvas & framerate configuration in the setup method and replace with our own
 */
export function sanitizeSetup(script: string, width: string, height: string) {
    let ast: Program;

    try {
        ast = parseScript(script);
    } catch (e) {
        return script;
    }

    for (let i = 0; i < ast.body.length; i++) {
        let statement = ast.body[i];

        if (statement.type === Syntax.FunctionDeclaration) {
            if (statement.id.name === "setup") {
                let setupBody: ExpressionStatement[] = [];

                let canvasAst = parseScript(`createCanvas(${width}, ${height})`);
                setupBody.push(canvasAst.body[0] as ExpressionStatement);

                statement.body.body.forEach((statement: ExpressionStatement) => {
                    if (statement.type === Syntax.ExpressionStatement && statement.expression.type === Syntax.CallExpression) {
                        let name = ((statement.expression as CallExpression).callee as Identifier | null)?.name;

                        if (name != "createCanvas" && name != "frameRate") {
                            setupBody.push(statement);
                        }
                    } else {
                        setupBody.push(statement);
                    }
                })

                let frameRateAst = parseScript("frameRate(30)");
                setupBody.push(frameRateAst.body[0] as ExpressionStatement);

                statement.body.body = setupBody;
            }
        }
    }

    return generate(ast);
}

export function resumeSketch() {
    userScript = document.createElement('script')
    userScript.textContent = scriptSrc
    document.body.appendChild(userScript);

    p5Inst = new p5(undefined, undefined);
}

export function pauseSketch() {
    let item = document.body.getElementsByClassName("p5Canvas")[0]
    item.remove()
    userScript.remove()
    p5Inst.remove()
}

const testScript = `
    let rectX = 0;
    let fr = 30;
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
        loadSketch(testScript, "540", "1110");
    }

    const removeButton = document.getElementById("removeButton")
    removeButton.onclick = () => {
        pauseSketch()
    }
}