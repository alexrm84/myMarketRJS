const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const HtmlWebpackTemplate = require('html-webpack-template');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

const publicPath = 'application';

module.exports =  ({
    devServer: {
        contentBase: `/${publicPath}/`,
        historyApiFallback: {
            rewrites: [{from: /./, to: `/index.html`}]
        },
        open: true,
        port: 9090,
        publicPath: `/`,
        proxy: [{
            context: ['/api', '/endpoint'],
            target: {
                host: "localhost",
                protocol: 'http:',
                port: 8190
            }
        }]
    },
    entry: {
        main: './src/main/js/app/index.jsx'
    },
    output: {
        filename: 'bundle.js',
        path: path.join(__dirname, 'src/main/resources/static'),
        publicPath: '/'
    },
    module: {
        rules: [
            {
                exclude: /node_modules/,
                include: path.join(__dirname, 'src/main/js/'),
                test: /\.(jsx|js)$/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env","@babel/preset-react"],
                        plugins: [
                            ["@babel/plugin-proposal-decorators", {"legacy": true}]
                        ]
                    }
                }
            },
            {
                test: /\.css$/,
                use: [MiniCssExtractPlugin.loader, 'css-loader']
            },
            {
                test: /\.less$/,
                use: [MiniCssExtractPlugin.loader, 'css-loader', 'less-loader']
            },
            {
                test: /\.(ico|png|gif|jpe?g)$/,
                use: {
                    loader: 'file-loader',
                    options: {name: 'assets/images/[name]/[hash].[ext]'}
                }
            },
            {
                test: /\.(svg|woff|woff2|eot|ttf)$/,
                use: {
                    loader: 'file-loader',
                    options: {name: 'assets/fonts/[name]/[hash].[ext]'}
                }
            },
            {test: /\.html$/, use: 'html-loader'},
        ]
    },
    resolve: {
        extensions: ['.js', '.jsx'],
        modules: ['node_modules', 'src/main/js'],
        symlinks: false
    },
    plugins: [
        new webpack.DefinePlugin({
            'process.env': {ASSET_PATH: JSON.stringify(publicPath)}
        }),
        new MiniCssExtractPlugin({
            filename: 'assets/stylesheets/[name]/[hash].css'
        }),
        new HtmlWebpackPlugin({
//            appMountId: 'root',
            inject: 'body',//false,
//            template: HtmlWebpackTemplate,
            template: "./src/main/js/app/index.html"
//            title: 'React-Start'
        })
    ]
});