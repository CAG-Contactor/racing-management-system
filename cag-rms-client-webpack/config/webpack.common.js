const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const options = require('yargs').argv;

const clientApi = options['client-api'] || process.env.CLIENT_API || "http://localhost:10580";
const buildInfo = options['build-info'] || process.env.BUILD_INFO || "Local development";
console.log('clientApi:', clientApi, 'buildInfo:', buildInfo);

module.exports = {
  entry: {
    // Här anger vi att huvudmodulen som skall inkluderas är src/Main.ts.
    // Denna skall sedan importera (direkt eller indirekt) resten av applikationen
    // Dessutom anger vi att src/vendor.ts skall inkluderas vilken i sin tur anger
    // 3PP-libbar
    "vendor": "./src/vendor.ts",
    "app": "./src/app.ts"
  },
  resolve: {
    // Webpack skall följande filändelser för att lösa upp moduler
    extensions: ['', '.ts', '.js']
  },
  module: {
    loaders: [
      {
        // Ange att awesome-typescript-loader skall användas för att ladda typescriptfiler
        // vid undling. Den kommer då att kompilera coh skapa source-maps m.m
        test: /\.ts$/,
        loader: "awesome-typescript-loader"
      },
      {
        // Ange loader för html-filer
        test: /\.html$/,
        exclude: /node_modules/,
        loader: "html-loader?exportAsEs6Default"
      },
      {
        test: /\.css$/,
        include: './src/**/*.css',
        loader: 'style!css'
      },
      {
        test: /\.css$/,
        exclude: './src',
        loader: ExtractTextPlugin.extract('style', 'css?sourceMap')
      },
      {
        test: /\.less$/i,
        include: './src/**/*.less',
        loader: 'style!css!less'
      },
      {
        test: /\.less$/i,
        exclude: './src',
        loader: ExtractTextPlugin.extract('style', 'css?sourceMap!less?sourceMap')
      },
      {
        test: /\.(png|jpe?g|gif|svg|woff|woff2|ttf|eot|ico)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loader: 'file?name=assets/[name].[hash].[ext]'
      },
      {
        test: /\.json$/i,
        loader: 'json'
      }
    ]
  },
  plugins: [
    // Ta in parametrar till applikationen
    new webpack.DefinePlugin({
      CLIENT_API: JSON.stringify(clientApi),
      BUILD_INFO: JSON.stringify(buildInfo)
    }),
    // Definiera att html-webpack-plugin skall processa index.html, m.a.p filnamn
    new HtmlWebpackPlugin({
      template: 'src/index.html'
    }),
    new ExtractTextPlugin('[name].[hash].css')
  ]
};
