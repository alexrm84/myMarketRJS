import React from 'react';
import axios from 'axios';

export default class Products extends React.Component {
    constructor(props) {
        super(props);

        this.handleClick = this.handleClick.bind(this);
    }
    handleClick(event){
        const value = event.target.value;
        console.log(event.target.value);
        axios.get('http://127.0.0.1:8190/app/api/v1/cart/add', {
            params: {
                    id: value
                }
            })
            .then(response => {
                this.setState()
            })
            .catch(error => {
                console.log(error)
        });
        event.preventDefault();
    }

    render() {
        return <div className="container">
            <h2>Каталог продуктов</h2>
            <table className="table table-hover">
                <thead className="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Наименование</th>
                        <th>Цена</th>
                        <th>Фото</th>
                        <th>Категория</th>
                        <th>Действия</th>
                    </tr>
                </thead>
                <tbody>
                    {this.props.products.map((product) =>
                        <tr key={product.id}>
                            <th>{product.id}</th>
                            <th>{product.title}</th>
                            <th>{product.price}</th>
                            <th>{product.image.id}/{product.image.path}</th>
                            <th>{product.category.title}</th>
                            <th>
                                <button value={product.id} onClick={this.handleClick} >В корзину</button>
                            </th>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    }
}

