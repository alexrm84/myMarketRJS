import React from 'react';

export default class Navigation extends React.Component {
    render() {
        let menus = [
            'Home',
            'Shop',
            'Cart'
        ]
        return <div className="container" fragment="navigation(activeTab)" >
            <nav className="navbar navbar-expand-lg navbar-light bg-light">
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav">
                        {menus.map((value)=>{
                            return <div key={value.toString()}>
                                <li className="nav-item" >
                                    <Link label={value} />
                                </li>
                            </div>
                        })}
                    </ul>
                </div>
            </nav>
        </div>
    }
}

class Link extends React.Component {
    render(){
        const url = '/' + this.props.label.toLowerCase().trim();
        return <div>
            <a className="nav-link" href={url}>{this.props.label}</a>
        </div>
    }

}