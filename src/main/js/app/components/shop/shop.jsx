import React from 'react';
import axios from 'axios';

import Filter from './filter'
import Products from './products'

export default class Shop extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            catId: '',
            word: '',
            minPrice: '',
            maxPrice: '',
            products: []
        };
        this.updateData = this.updateData.bind(this);
        this.reloadData = this.reloadData.bind(this);
    }
    componentDidMount() {
        axios.get('http://127.0.0.1:8190/app/api/v1/shop/page')
            .then(response => {
                this.setState({products: response.data.content})
            })
            .catch(error => {
                console.log(error)
        })
    }
     updateData(target) {
            const value = target.value;
            const name = target.name;
            this.setState({[name]: value});
    }

    reloadData(){
        axios.get('http://127.0.0.1:8190/app/api/v1/shop/page', {
                    params: {
                        catId: this.state.catId,
                        word: this.state.word,
                        minPrice: this.state.minPrice,
                        maxPrice: this.state.maxPrice
                    }
                })
                .then(response => {
                this.setState({products: response.data.content})
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

        </div>
    }

}


//<body>
//    <div class="container">

 //       <br>


//
//        <nav aria-label="Page navigation example">
//            <ul class="pagination">
//                <li class="page-item" th:classappend="${!(page.getNumber() > 0) ? 'disabled' : ''}">
//                    <a th:class="page-link" th:href="@{'/shop?currentPage=' + ${page.getNumber()} + ${filters}}" th:text="#{paging.prev}"/>
//                </li>
//                <li class="page-item" th:classappend="${page.getNumber() + 1 == i ? 'active' : ''}" th:each="i : ${#numbers.sequence(1, page.getTotalPages())}">
//                    <a class="page-link" th:href="@{'/shop?currentPage=' + ${i} + ${filters}}" th:text=${i} />
//                </li>
//                <li class="page-item" th:classappend="${!(page.getNumber() < page.getTotalPages() - 1) ? 'disabled' : ''}">
 //                   <a th:class="page-link" th:href="@{'/shop?currentPage=' + ${page.getNumber() + 2} + ${filters}}" th:text="#{paging.next}"/>
 //               </li>
 //           </ul>
 //       </nav>

 //       <div th:unless="${viewHistoryProducts==null}">
 //           <h2 th:text="#{products.viewsHistory}"/>
 //           <table class="table table-hover">
 //               <thead class="thead-dark">
 //               <tr>
 //                   <th></th>
 //                   <th th:text="#{products.columnNames.title}"></th>
 //                   <th th:text="#{products.columnNames.price}"></th>
 //               </tr>
 //               </thead>
 //               <tbody>
 //               <tr th:each="p : ${viewHistoryProducts}">
 //                   <td>
 //                       <img class="table_image" th:src="@{'/images/products/' + ${p.id} + '/img_1.jpg'}">
 //                   </td>
 //                   <td><a th:href="@{'/products/' + ${p.id}}" th:text="${p.title}"/></td>
 //                   <td th:text="${p.price}"/>
 //               </tr>
 //               </tbody>
 //           </table>
 //       </div>
 //   </div>
//</body>