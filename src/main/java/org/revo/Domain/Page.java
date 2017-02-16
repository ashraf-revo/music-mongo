package org.revo.Domain;

import org.springframework.data.domain.PageRequest;

/**
 * Created by ashraf on 31/01/17.
 */
public class Page {
    private int number=0;
    private int size=10;

    public Page() {
    }

    public Page(int number, int size) {
        this.number = number;
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

   public PageRequest getPageRequest() {
        return new PageRequest(this.getNumber(), this.getSize());
    }
}