const path = require('path');

module.exports = {
    entry: ['./src/index.ts'],
    devtool: 'inline-source-map',
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
        alias: {
            // add as many aliases as you like! 
            src: path.resolve(__dirname, 'src'),
            lib: path.resolve(__dirname, 'src/lib')
        }
    },
    output: {
        library: 'p5-ts',
        libraryTarget: 'umd',
        filename: 'index.js',
        path: path.resolve(__dirname, 'lib'),

    },
    devServer: {
        contentBase: path.join(__dirname, 'lib'),
        compress: true,
        port: 9000,
    }
};