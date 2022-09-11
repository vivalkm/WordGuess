package com.vivalkm.wordle

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var guess1TextView: TextView
    private lateinit var guess1WordTextView: TextView
    private lateinit var guess1CheckTextView: TextView
    private lateinit var guess1CheckResultTextView: TextView
    private lateinit var guess2TextView: TextView
    private lateinit var guess2WordTextView: TextView
    private lateinit var guess2CheckTextView: TextView
    private lateinit var guess2CheckResultTextView: TextView
    private lateinit var guess3TextView: TextView
    private lateinit var guess3WordTextView: TextView
    private lateinit var guess3CheckTextView: TextView
    private lateinit var guess3CheckResultTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var targetWordTextView: TextView
    private lateinit var textViews:Array<TextView>

    private lateinit var guessBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var guessWordEditText: EditText

    private lateinit var targetWord:String
    private var counter = 0

    private lateinit var guessWord1:String
    private lateinit var guessWord2:String
    private lateinit var guessWord3:String
    private lateinit var curGuessWord:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guess1TextView = findViewById(R.id.guess1TextView)
        guess1WordTextView = findViewById(R.id.guess1WordTextView)
        guess1CheckTextView = findViewById(R.id.guess1CheckTextView)
        guess1CheckResultTextView = findViewById(R.id.guess1CheckResultTextView)
        guess2TextView = findViewById(R.id.guess2TextView)
        guess2WordTextView = findViewById(R.id.guess2WordTextView)
        guess2CheckTextView = findViewById(R.id.guess2CheckTextView)
        guess2CheckResultTextView = findViewById(R.id.guess2CheckResultTextView)
        guess3TextView = findViewById(R.id.guess3TextView)
        guess3WordTextView = findViewById(R.id.guess3WordTextView)
        guess3CheckTextView = findViewById(R.id.guess3CheckTextView)
        guess3CheckResultTextView = findViewById(R.id.guess3CheckResultTextView)
        resultTextView = findViewById(R.id.resultTextView)
        targetWordTextView = findViewById(R.id.targetWordTextView)
        
        guessBtn = findViewById(R.id.guessBtn)
        resetBtn = findViewById(R.id.resetBtn)
        guessWordEditText = findViewById(R.id.guessWordEditText)

        textViews = arrayOf(
            guess1TextView,
            guess1WordTextView,
            guess1CheckTextView,
            guess1CheckResultTextView,
            guess2TextView,
            guess2WordTextView,
            guess2CheckTextView,
            guess2CheckResultTextView,
            guess3TextView,
            guess3WordTextView,
            guess3CheckTextView,
            guess3CheckResultTextView,
            targetWordTextView,
            resultTextView
        )

        newGame()

        guessBtn.setOnClickListener {
            curGuessWord = guessWordEditText.text.toString().uppercase()
            hideKeyboard(this)
            guessWordEditText.setText("")

            if (!isValidWord(curGuessWord)) {
                Toast.makeText(
                    it.context,
                    "The word must contain exactly 4 letters, try again!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                counter++
                when (counter) {
                    1 -> {
                        guessWord1 = curGuessWord

                        guess1TextView.text = "Guess # $counter"
                        guess1WordTextView.text = guessWord1
                        guess1CheckTextView.text = "Guess # $counter Check"
                        guess1CheckResultTextView.text = checkGuess(curGuessWord, targetWord)

                        guess1TextView.isVisible = true
                        guess1WordTextView.isVisible = true
                        guess1CheckTextView.isVisible = true
                        guess1CheckResultTextView.isVisible = true
                    }
                    2 -> {
                        guessWord2 = curGuessWord

                        guess2TextView.text = "Guess # $counter"
                        guess2WordTextView.text = guessWord2
                        guess2CheckTextView.text = "Guess # $counter Check"
                        guess2CheckResultTextView.text = checkGuess(curGuessWord, targetWord)

                        guess2TextView.isVisible = true
                        guess2WordTextView.isVisible = true
                        guess2CheckTextView.isVisible = true
                        guess2CheckResultTextView.isVisible = true
                    }
                    else -> {
                        guessWord3 = curGuessWord

                        guess3TextView.text = "Guess # $counter"
                        guess3WordTextView.text = guessWord3
                        guess3CheckTextView.text = "Guess # $counter Check"
                        guess3CheckResultTextView.text = checkGuess(curGuessWord, targetWord)

                        guess3TextView.isVisible = true
                        guess3WordTextView.isVisible = true
                        guess3CheckTextView.isVisible = true
                        guess3CheckResultTextView.isVisible = true

                        targetWordTextView.isVisible = true
                        guessBtn.isVisible = false
                        resetBtn.isVisible = true

                        showResult()
                    }
                }
            }
        }

        resetBtn.setOnClickListener {
            newGame()
        }
    }

    /**
     * Show message based on final result
     */
    private fun showResult() {
        if (targetWord == curGuessWord) {
            resultTextView.text = getString(R.string.winText)
        } else {
            resultTextView.text = getString(R.string.loseText)
        }
        resultTextView.isVisible = true
    }

    /**
     * Start a new game
     */
    private fun newGame() {
        // hide all TextViews
        for (view in textViews) {
            view.isVisible = false
            view.text = ""
        }

        // reset target word
        targetWord = FourLetterWordList.getRandomFourLetterWord()
        targetWordTextView.text = targetWord

        // reset counter
        counter = 0

        guessBtn.isVisible = true
        resetBtn.isVisible = false
    }
    
    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String, targetWord: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == targetWord[i]) {
                result += "O"
            } else if (guess[i] in targetWord) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }

    /**
     * Hide keyboard when EditText does not have focus
     */
    private fun hideKeyboard(context: Context) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = (context as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    /**
     * Return true if the given word contains excatly 4 alphabetical letters
     */
    private fun isValidWord(word: String): Boolean {
        if (word.length != 4) return false
        for (c in word) {
            if (c < 'A' || c > 'Z') return false
        }
        return true
    }
}