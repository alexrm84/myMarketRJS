import React from 'react';
import Navigation from 'app/components/navigation';
import 'bootstrap/dist/css/bootstrap.css';
import { Switch, Route } from 'react-router-dom';

import Home from 'app/components/home/home';
import Shop from 'app/components/shop/shop';
import Cart from 'app/components/cart/cart';

//import { Router, Route, hashHistory } from 'react-router';
//import { Provider } from 'react-redux';
//import {routerMiddleware, ConnectedRouter} from 'react-router-redux';
//import {createBrowserHistory} from 'history';
//import {Provider} from 'react-redux';
//import {createStore, applyMiddleware} from 'redux';
//import {withRouter} from 'react-router-dom';
//import {composeWithDevTools} from 'redux-devtools-extension';
//import LocaleProvider from 'app/application/localeProvider'
//import {useTranslation, Trans} from 'react-i18next';
//import {createStackNavigator} from 'react-navigation';
//import IndexLayout from 'app/components/layout';
//import {reducers} from 'app/reduser/redusers';

function Main() {
    return(
        <main>
            <Switch>
                <Route path='/home' component={Home} />
                <Route path='/shop' component={Shop} />
                <Route path='/cart' component={Cart} />
            </Switch>
        </main>
    );
}

export default function Application() {
    return (
        <div>
        <Navigation />
        <Main />
        </div>
    );
}



