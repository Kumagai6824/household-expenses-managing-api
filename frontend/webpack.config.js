const HtmlWebpackPlugin = require("html-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");
const { Resolver } = require("webpack");

module.exports = {
  entry: "./src/js/app.ts",
  output: {
    path: `${__dirname}/dist/`,
    filename: "bundle.js",
  },
  mode: "development",
  devtool: "source-map",
  devServer: {
    static: {
      directory: "./dist",
    },
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        use: [MiniCssExtractPlugin.loader, "css-loader"],
      },
      {
        //拡張子 .ts の場合
        test: /\.ts$/,
        //TypeScriptをコンパイルする
        use: "ts-loader",
      },
    ],
  },
  plugins: [
    new HtmlWebpackPlugin({
      //テンプレートに使用するhtmlファイルを指定
      template: "./src/index.html",
    }),
    new MiniCssExtractPlugin(),
  ],
  resolve: {
    //拡張子を配列で指定
    extensions: [".ts", ".js"],
  },
};
