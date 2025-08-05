const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
  entry: "./frontend/js/app.js",
  output: {
    path: `${__dirname}/dist/`,
    filename: "bundle.js",
  },
  mode: "development",
  devtool: "source-map",
  module: {
    rules: [
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"],
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      //テンプレートに使用するhtmlファイルを指定
      template: "./frontend/index.html",
    }),
  ],
};
