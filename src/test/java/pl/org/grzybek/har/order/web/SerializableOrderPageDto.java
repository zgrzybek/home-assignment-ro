package pl.org.grzybek.har.order.web;

import pl.org.grzybek.har.order.dto.OrderDto;

import java.util.List;

public class SerializableOrderPageDto {
    private List<OrderDto> content;
    private Object pageable;
    private Object last;
    private Object totalElements;
    private Integer totalPages;
    private Object first;
    private Object sort;
    private Object numberOfElements;
    private Integer size;
    private Object number;

    public List<OrderDto> getContent() {
        return content;
    }

    public void setContent(List<OrderDto> content) {
        this.content = content;
    }

    public Object getPageable() {
        return pageable;
    }

    public void setPageable(Object pageable) {
        this.pageable = pageable;
    }

    public Object getLast() {
        return last;
    }

    public void setLast(Object last) {
        this.last = last;
    }

    public Object getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Object totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Object getFirst() {
        return first;
    }

    public void setFirst(Object first) {
        this.first = first;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public Object getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Object numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Object getNumber() {
        return number;
    }

    public void setNumber(Object number) {
        this.number = number;
    }
}
