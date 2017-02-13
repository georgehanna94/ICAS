package app.example.icas.integratedconcussionassessmentsystem;

/**
 * Created by mkaka on 2016-12-05.
 */

public class symptomEvaluationQuestions {

    String questions[] = {
            "Headache",
            "Pressure in head",
            "Neck Pain",
            "Nausea or vomiting",
            "Dizziness",
            "Blurred vision",
            "Balance problems",
            "Sensitivity to light",
            "Sensitivity to noise",
            "Feeling slowed down",
            "Feeling like “in a fog“",
            "“Don’t feel right”",
            "Difficulty \nconcentrating",
            "Difficulty \nremembering",
            "Fatigue or low energy",
            "Confusion",
            "Drowsiness",
            "Trouble falling asleep",
            "More emotional",
            "Irritability",
            "Sadness",
            "Nervous or anxious"
    };
    int index, max;

    /* Constructor */
    public symptomEvaluationQuestions() {
        this.index = 0;
        this.max = questions.length;
    }

    public String getCurrentQuestion(){
        return questions[this.index];
    }

    public void incrementIndex(){
        if(this.index < this.max) {
            this.index++;
        }
    }

    public void decrementIndex(){
        if(this.index > 0){
            this.index --;
        }
    }

    /* getters and setters */
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMaxIndex(){
        return this.max;
    }

}
