const testScript = `
        function setup() {
            createCanvas(400, 400);
        }

        function draw() {
            background(220);
            ellipse(50,50,80,80);
        }
`

window.onload = () => {
    const firstButton = document.getElementById("testButton")
    firstButton.onclick = () => {

        let userScript = document.createElement('script')
        userScript.textContent = testScript

        const p5Path = "https://cdn.jsdelivr.net/npm/p5@1.6.0/lib/p5.js"
        let script = document.createElement('script');
// script.onload = cb;
        script.onerror = () => {
            console.log("Failed to load script: " + p5Path);
        };
        script.setAttribute('src', p5Path);

        document.body.appendChild(userScript);
        document.body.appendChild(script);
    }
}