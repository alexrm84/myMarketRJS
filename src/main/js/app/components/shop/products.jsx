import React from 'react';
import axios from 'axios';

export default class Products extends React.Component {
    constructor(props) {
        super(props);
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
                    </tr>
                </thead>
                <tbody>
                    {this.props.products.map((product) =>
                        <tr key={product.id}>
                            <th>{product.id}</th>
                            <th>{product.title}</th>
                            <th>{product.price}</th>
                            <th>{product.images[0].id}/{product.images[0].path}</th>
                            <th>{product.category.title}</th>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    }
}

