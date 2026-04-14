public class HealthData {
    private double averageHealthScore;
    private double averageStressScore;
    private double breakAdherencePercentage;

    public HealthData(double averageHealthScore, double averageStressScore, double breakAdherencePercentage) {
        this.averageHealthScore = averageHealthScore;
        this.averageStressScore = averageStressScore;
        this.breakAdherencePercentage = breakAdherencePercentage;
    }

    public double getAverageHealthScore() {
        return averageHealthScore;
    }

    public double getAverageStressScore() {
        return averageStressScore;
    }

    public double getBreakAdherencePercentage() {
        return breakAdherencePercentage;
    }

    public double calculateCombinedScore() {
        return (averageHealthScore + (100 - averageStressScore) + breakAdherencePercentage) / 3.0;
    }
}
