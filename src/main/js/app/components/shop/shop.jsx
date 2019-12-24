import React from 'react';

export default function Shop() {
    return <div className="container">
        <h2>Фильтры</h2>
    </div>
}


//<body>
//    <div class="container">
//        <form th:action="@{/shop}" method="get">
//            <label for="productCategory" th:text="#{filters.category}" />
//            <select id="productCategory" th:name = "catId">
//                <option th:text="' '" th:value="null"/>
//                <option th:each="cat : ${categories}" th:text="${cat.title}" th:value="${cat.id}"/>
//<!--                <option th:each="cat : ${categories}" th:text="#{filters.category.${cat.title}}" th:value="${cat.id}"/>-->
//            </select>
//            <div class="input-group">

//                <br>
//                <input th:value="${param.word != null} ? ${param.word}" class="form-control" th:name="word" type="text" th:placeholder="#{filters.word}">
//                <input th:value="${param.minPrice != null} ? ${param.minPrice}" class="form-control" th:name="minPrice" th:type="number" th:placeholder="#{filters.price.min}">
//                <input th:value="${param.maxPrice != null} ? ${param.maxPrice}" class="form-control" th:name="maxPrice" th:type="number" th:placeholder="#{filters.price.max}">
//            </div>
//            <button type="submit" class="btn btn-primary" th:text="#{filters.submit}"></button>
 //       </form>
 //       <br>

//        <h2 th:text="#{products.list}"/>
//        <table class="table table-hover">
//            <thead class="thead-dark">
 //           <tr>
  //              <th>ID</th>
 //               <th></th>
 //               <th th:text="#{products.columnNames.title}"></th>
 //               <th th:text="#{products.columnNames.price}"></th>
 //               <th th:text="#{products.columnNames.actions}"></th>
 //           </tr>
 //           </thead>
//            <tbody>
//            <tr th:each="p : ${page.getContent()}">
//                <td th:text="${p.id}"/>
//                <td>
//                    <img class="table_image" th:src="@{'/images/products/' + ${p.id} + '/img_1.jpg'}">
//                </td>
//                <td><a th:href="@{'/shop/details?id=' + ${p.id}}" target="_blank" th:text="${p.title}" /></td>
//                <td th:text="${p.price}"/>
//                <td>
//                    <a th:id="'shopBtnAddToCart' + ${p.id}" class="btn btn-primary" th:href="@{'/cart/add?id=' + ${p.id}}"  th:text="#{products.actions.addToCart}" />
//                </td>
//            </tr>
//            </tbody>
//        </table>
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