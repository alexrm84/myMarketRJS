import React from 'react';

export default class Pagination extends React.Component {
    constructor(props) {
        super(props);


        this.handleClick = this.handleClick.bind(this);
    }
    handleClick(event){
        const target = event.target;
        this.props.updateData(target);
        this.props.reloadData();

    }
    render(){
        return <div className="container">
            <nav aria-label="Page navigation example">
                <ul className="pagination">
                    <button className="page-item" type="button" className="btn btn-outline-primary" name="currentPage" value={Number(this.props.currentPage)-1} onClick={this.handleClick} >Prev</button>
                    {[...Array(this.props.totalPages)].map((p,index) =>
                        <button key={index} className="page-item"  type="button" className="btn btn-outline-primary" name="currentPage" value={index+1} onClick={this.handleClick}>{index+1}</button>
                    )}
                    <button className="page-item" type="button" className="btn btn-outline-primary" name="currentPage" value={Number(this.props.currentPage)+1} onClick={this.handleClick}>Next</button>
                </ul>
            </nav>
        </div>
    }
}
//{(this.props.currentPage)<2 ? 'disabled' : ''} - доделать