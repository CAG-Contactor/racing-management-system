const webpackMerge = require('webpack-merge');
const commonConfig = require('./webpack.common.js');

// Här använder vi webpack-merge för att inkludera gemensam konf:
module.exports = webpackMerge(commonConfig, {
  // Ange att vi skapar source maps med en metod som är lite snabbare än full source maps
  devtool: 'cheap-module-eval-source-map',

  // Devserver kommer att generera allt i minnet ist.f till disk
  output: {
    // Ange URL relativt vilken script m.m servas från
    publicPath: 'http://localhost:8080/',
    filename: '[name].js',
    chunkFilename: '[id].chunk.js'
  },

  // Lite konf för webpack devserver
  devServer: {
    historyApiFallback: true,
    stats: 'minimal'
  }
});