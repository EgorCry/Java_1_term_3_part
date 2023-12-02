package Task_1;

public class DataSensor {
    private int temp;
    private int carbonDioxide;

    public DataSensor(int temp, int carbonDioxide) {
        this.temp = temp;
        this.carbonDioxide = carbonDioxide;
    }

    public int getTempReading() {
        return temp;
    }

    public void setTempReading(int temp) {
        this.temp = temp;
    }

    public int getCarbonDioxideReading() {
        return carbonDioxide;
    }

    public void setCarbonDioxideReading(int carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }
}
