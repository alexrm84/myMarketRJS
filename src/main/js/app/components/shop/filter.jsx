import React from 'react';
import axios from 'axios';

export default class Filter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            categories: [],
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    componentDidMount() {
            axios.get('http://127.0.0.1:8190/app/api/v1/shop/cat')
                .then(response => {
                    this.setState({categories: response.data})
                })
                .catch(error => {
                    console.log(error)
            })
        }
    handleChange(event){
        const target = event.target;
        this.props.updateData(target);
    }
    handleSubmit(event){
        console.log("tikTak");
        console.log(this.state.catId);
        this.props.reloadData();
        event.preventDefault();
    }
    render() {
        return <div className="container">
            <h2>Фильтры</h2>
            <form onSubmit={this.handleSubmit}>
                <label>
                    Категория товара
                    <select name="catId" value={this.state.catId} onChange={this.handleChange}>
                        <option value={this.state.catId}>All</option>
                        {this.state.categories.map((cat) =>
                            <option key={cat.id} value={cat.id}>{cat.title}</option>
                        )}
                    </select>
                </label>
                <div className="input-group">
                    <input className="form-control" name="word" type="text" placeholder= {(this.props.word)==='' ? "Ключевое слово" : '{this.props.word}'}  value={this.state.word} onChange={this.handleChange} />
                    <input className="form-control" name="minPrice" type="number" placeholder="Минимальная цена" value={this.state.minPrice} onChange={this.handleChange} />
                    <input className="form-control" name="maxPrice" type="number" placeholder="Максимальная цена" value={this.state.maxPrice} onChange={this.handleChange} />
                </div>
                <input type="submit" value="Применить" />
            </form>
        </div>
    }
}