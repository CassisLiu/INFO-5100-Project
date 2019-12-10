package m3.model.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import m3.mock.Vehicle;
import m3.model.checker.Checker;


@JsonTypeInfo(use = Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = BrandFilter.class),
	@Type(value = BrandsFilter.class),
	@Type(value = ColorFilter.class),
	@Type(value = ColorsFilter.class),
	@Type(value = ModelFilter.class),
	@Type(value = ModelsFilter.class),
	@Type(value = ListFilter.class),
	@Type(value = PriceFilter.class),
	@Type(value = PricesFilter.class),
	@Type(value = ValueFilter.class),
	@Type(value = VehicleIDFilter.class),
	@Type(value = VehicleIDsFilter.class),
	@Type(value = YearFilter.class),
	@Type(value = YearsFilter.class)
})

public abstract class Filter<T> {
    protected Checker<T> checker;
    @JsonCreator
    public Filter(@JsonProperty("checker")Checker<T> checker) {
        this.checker = checker;
    }

	abstract public T getVehicleValue(Vehicle vehicle);

    public String checkerToString()
    {
    	return checker.ToString();
    }

    abstract public boolean isApplicable(Vehicle vehicle);

    abstract public boolean isApplicable(T value);
}
