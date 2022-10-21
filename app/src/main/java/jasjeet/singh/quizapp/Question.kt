package jasjeet.singh.quizapp

data class Question(
    val id: Int,
    val question: String,
    val image: Int,
    // "R.drawable.imageName" are returned as Int and we can see this by printing in a kotlin file.
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAnswer: Int
)
/*
 * This file is basically a format of how a question should look like.
 * This is the model of all questions in the app and this class will
 * be used to store the data of every question.
 */