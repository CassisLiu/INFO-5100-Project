package m3.model.filter;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import m3.mock.Vehicle;
import m3.model.checker.Checker;

public abstract class ListFilter<T> extends Filter<T> {
    private List<T> list;
    @JsonCreator
    public ListFilter(@JsonProperty("checker")Checker<T> checker) {
        super(checker);
    }
    
    @JsonCreator
    public ListFilter(@JsonProperty("list")List<T> list, @JsonProperty("checker")Checker<T> checker) {
        super(checker);
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public abstract T getValueFromString(String string) throws InputException;

    public void setListFromString(String string) throws InputException {
        String[] strings = string.split(",");
        List<T> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            list.add(this.getValueFromString(strings[i]));
        }
        this.setList(list);
    }

    @Override
    public boolean isApplicable(T value) {
        for (T t : list) {
            if (this.checker.check(value, t))
                return true;
        }
        return false;
    }

    @Override
    public boolean isApplicable(Vehicle vehicle) {
        return this.isApplicable(this.getVehicleValue(vehicle));
    }
}
