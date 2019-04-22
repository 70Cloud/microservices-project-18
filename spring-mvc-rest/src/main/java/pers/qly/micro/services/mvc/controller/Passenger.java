package pers.qly.micro.services.mvc.controller;

import java.util.List;

/**
 * @Author: NoNo
 * @Description:
 * @Date: Create in 17:26 2019/4/13
 */
public class Passenger {

    private List<String> types;

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "types=" + types +
                '}';
    }
}
