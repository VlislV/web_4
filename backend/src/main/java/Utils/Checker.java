package Utils;

import DTOs.PointDTO;
import jakarta.xml.bind.ValidationException;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class Checker {
    private final AtomicReference<Integer> X = new AtomicReference<Integer>();
    private final AtomicReference<Double> Y = new AtomicReference<Double>();
    private final AtomicReference<Integer> R = new AtomicReference<Integer>();

    private final Integer[] validX ={-5, -4, -3, -2, -1, 0, 1, 2, 3};
    private final Integer[] validR ={1, 2, 3};


    private void validate() throws ValidationException {
        if(!Arrays.asList(validX).contains(X.get())){
            throw new ValidationException("Invalid X value");
        }
        if(Y.get() < -3 || Y.get() > 3){
            throw new ValidationException("Y must be between -3 and 3");
        }
        if(!Arrays.asList(validR).contains(R.get())){
            throw new ValidationException("Invalid R value");
        }
    }
    public String check(PointDTO point) throws Exception
    {
        X.set(Integer.parseInt(point.getX().toString()));
        Y.set(Double.parseDouble(point.getY().toString()));
        R.set(Integer.parseInt(point.getR().toString()));
        validate();
        if (Y.get() <= 0 && X.get() <= 0 && Y.get() >= -2 * X.get() - R.get())
            return "HIT in triangle";
        if (Y.get() <= 0 && X.get() >= 0 && X.get() <= R.get() && Y.get() <= R.get())
            return "HIT in rectangle";
        if (X.get() <= 0 && Y.get() >= 0 && X.get() * X.get() + Y.get() * Y.get() <= (double) R.get() * R.get())
            return "HIT in circle";
        return "MISS";
    }
}
