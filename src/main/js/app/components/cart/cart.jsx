import React from 'react';
import axios from 'axios';

export default class Cart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            items: []
        };

        this.handleClick = this.handleClick.bind(this);
    }
    componentDidMount() {
        axios.get('http://127.0.0.1:8190/app/api/v1/cart')
            .then(response => {
                console.log(response.data);
                this.setState({items: response.data})
            })
            .catch(error => {
                console.log(error)
        })
    }

    handleClick(event){
        const value = event.target.value;
        axios.get('http://127.0.0.1:8190/app/api/v1/cart/reduce', {
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
            <h1>Корзина</h1>
            {(this.state.items.size)===0 ? <h5>Корзина пуста</h5> : ''}

                <table  className="table table-hover">
                    <thead className="thead-dark">
                    <tr>
                        <th>Наименование</th>
                        <th>Цена</th>
                        <th>Количество</th>
                        <th>Итого</th>
                        <th>Действия</th>
                    </tr>
                    </thead>
                    <tbody>
                        {this.state.items.map((item) =>
                            <tr key={items.id}>
                                <th>{item.product.title}</th>
                                <th>{item.product.price}</th>
                                <th>{item.quantity}</th>
                                <th>{item.totalPrice}</th>
                                <th>
                                    <button value={product.id} onClick={this.handleClick} >Удалить</button>
                                </th>
                            </tr>
                        )}
                    </tbody>
                </table>
                <button  >Оформить заказ</button>

        </div>
    }
}
