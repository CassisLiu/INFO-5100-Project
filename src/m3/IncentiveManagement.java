package m3;

import java.util.List;

import m3.mock.Vehicle;
import m3.model.Incentive;
import m3.model.IncentivesFinalPrice;

public interface IncentiveManagement {

    public List<List<Incentive>> getVehicleIncentives(Vehicle[] vehicles);

    public List<IncentivesFinalPrice> getVehicleFinalIncentives(Vehicle[] vehicles);
}
