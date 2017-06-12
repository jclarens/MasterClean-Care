const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const noVisualization = process.env.NODE_ENV === 'production' 
        || process.argv.slice(-1)[0] == '-p'
        || process.argv.some(arg => arg.indexOf('webpack-dev-server') >= 0);

const HtmlWebpackPluginConfig = new HtmlWebpackPlugin({
  template: './client/index.html',
  filename: 'index.html',
  inject: 'body'
})

const extractLess = new ExtractTextPlugin({
    filename: "[name].[contenthash].css",
    disable: noVisualization
});

const config = {
  entry: {
    main: './client/index.js'
  },
  output: {
    path: path.resolve(__dirname, 'destination'),
    filename: '[name]-bundle.js'  
  },
  resolve: {
    alias: {
      'jscolor': 'util/jscolor.js'
    },
    modules: [
      path.resolve('./'),
      path.resolve('./node_modules')
    ]
  },
  module: {
    loaders: [
      // jsx loader
      { test: /\.js$/, loader: 'babel-loader', exclude: /node_modules/ },
      { test: /\.jsx$/, loader: 'babel-loader', exclude: /node_modules/ },
      {
          test: /\.es6$/,
          include: /materialize/,
          loader: 'babel-loader',
          query: {
              presets: ['react', 'es2015-rollup', 'stage-1', 'stage-2'],
              plugins: ['transform-decorators-legacy', 'external-helpers']
          }
      },
      // less loader
      {
        test: /\.less$/,
        use: extractLess.extract({
              use: [{
                  loader: "css-loader"
              }, {
                  loader: "less-loader"
              }],
              // use style-loader in development
              fallback: "style-loader"
          })
      },

      {
        test: /\.css$/,
        loader: 'style-loader!css-loader!postcss-loader',
        include: path.join(__dirname, 'node_modules'), // oops, this also includes flexboxgrid
        exclude: /flexboxgrid/ // so we have to exclude it
      },
      
      // fonts loader
      {
        test: /\.(woff2|woff|ttf|eot|svg|otf)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
        loaders: ["url-loader?limit=100&name=fonts/[name].[ext]"]
      },

      // image loader
      {
        test: /\.(jpg|png|svg)$/,
        loader: 'url-loader',
        options: {
          limit: 25000
        }
      }
    ]
  },
  plugins: [
    HtmlWebpackPluginConfig,
    extractLess,
    (!noVisualization ? 
    new BundleAnalyzerPlugin() : null),

    new webpack.optimize.CommonsChunkPlugin({
      name: 'vendor',
      minChunks: function (module) {
        // this assumes your vendor imports exist in the node_modules directory
        return module.context && module.context.indexOf('node_modules') !== -1;
      }
    }),

    new webpack.optimize.CommonsChunkPlugin({
      name: 'react-build',
      minChunks(module, count) {
        var context = module.context;
        return context && (context.indexOf('node_modules\\react\\') >= 0 || context.indexOf('node_modules\\react-dom\\') >= 0);
      },
    }),

    new webpack.optimize.CommonsChunkPlugin({
      name: 'manifest'
    }),        

    //*********************************** async chunks*************************

    //catch all - anything used in more than one place
    new webpack.optimize.CommonsChunkPlugin({
        async: 'used-twice',
        minChunks(module, count) {
            return count >= 2;
        },
    }),

    // //specifically bundle these large things
    // new webpack.optimize.CommonsChunkPlugin({
    //     async: 'react-dnd',
    //     minChunks(module, count) {
    //         var context = module.context;
    //         var targets = ['react-dnd', 'react-dnd-html5-backend', 'react-dnd-touch-backend', 'dnd-core']
    //         return context && context.indexOf('node_modules') >= 0 && targets.find(t => new RegExp('\\\\' + t + '\\\\', 'i').test(context));
    //     },
    // }),
  ].filter(p => p),
  devServer: {
    historyApiFallback: true,
    proxy: {
      "/subject": "http://localhost:8088",
      "/tag": "http://localhost:8088",
      "/book": "http://localhost:8088",
      "/static": "http://localhost:8088"
    }
  }
}

module.exports = config;
