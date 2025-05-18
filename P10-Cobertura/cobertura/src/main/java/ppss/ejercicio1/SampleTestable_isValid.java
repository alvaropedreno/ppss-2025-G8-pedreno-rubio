package ppss.ejercicio1;

public class SampleTestable_isValid extends Sample {
    public boolean result_isValid;

    @Override
    public boolean isValid(int[] data) {
        if (data == null || data.length == 0) {
            return false;
        }
        int index = 0;
        final int min_value = 10;
        final int max_value = 80;
        boolean error = false;

        while (index < data.length && !error) {
            if (data[index] >= min_value && data[index] <= max_value) {
                index++;
            } else {
                error = true;
            }
        }
        return !error;
    }
}
