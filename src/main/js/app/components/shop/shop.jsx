import React from 'react';
import axios from 'axios';

import Filter from './filter';
import Products from './products';
import Pagination from './pagination';

export default class Shop extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            catId: '',
            word: '',
            minPrice: '',
            maxPrice: '',
            currentPage: 0,
            products: [],
            totalPages: 0
        };
        this.updateData = this.updateData.bind(this);
        this.reloadData = this.reloadData.bind(this);
    }
    componentDidMount() {
        axios.get('http://127.0.0.1:8190/app/api/v1/shop/page')
            .then(response => {
                this.setState({products: response.data.content})
        //        this.setState({currentPage: response.data.number+1})
                this.setState({totalPages: response.data.totalPages})
            })
            .catch(error => {
                console.log(error)
        })
    }
    updateData(target) {
        const value = target.value;
        const name = target.name;
        console.log('value=' + value)
        console.log('name=' + name)
        this.setState({[name]: value});
    }

    reloadData(){
        console.log('currentPage='+this.state.currentPage)
        axios.get('http://127.0.0.1:8190/app/api/v1/shop/page', {
                    params: {
                        catId: this.state.catId,
                        word: this.state.word,
                        minPrice: this.state.minPrice,
                        maxPrice: this.state.maxPrice,
                        currentPage: this.state.currentPage
                    }
                })
                .then(response => {
                this.setState({products: response.data.content})
        //        this.setState({currentPage: response.data.number+1})
                this.setState({totalPages: response.data.totalPages})
            })
            .catch(error => {
                console.log(error)
        });
    }

    render(){
        return <div className="container">
            <Filter updateData={this.updateData}
                    reloadData={this.reloadData}
                    catId={this.state.catId}
                    word={this.state.word}
                    minPrice={this.state.minPrice}
                    maxPrice={this.state.maxPrice} />
            <Products products={this.state.products} />
            <Pagination updateData={this.updateData}
                    reloadData={this.reloadData}
                    currentPage={this.state.currentPage}
                    totalPages={this.state.totalPages} />
        </div>
    }

}