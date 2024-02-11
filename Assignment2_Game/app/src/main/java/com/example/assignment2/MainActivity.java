package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static List<Integer> numbers = new ArrayList<>();
    public static final int total = 661461;
    int currentIndex = 0, count = 4, buttonCount = 0, sum = 0;
    int[] visited = new int[10];



    double offer;
    TextView textViewChooseCases;
    Button buttonDeal, buttonNoDeal, buttonReset;
    private ImageView imageViewSuitcase1, imageViewSuitcase2, imageViewSuitcase3, imageViewSuitcase4, imageViewSuitcase5, imageViewSuitcase6,
            imageViewSuitcase7, imageViewSuitcase8, imageViewSuitcase9, imageViewSuitcase10,
            imageViewReward_1, imageViewReward_10, imageViewReward_50, imageViewReward_100, imageViewReward_300, imageViewReward_1000,
            imageViewReward_10000, imageViewReward_50000, imageViewReward_100000, imageViewReward_500000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewChooseCases = findViewById(R.id.textViewChooseCases);

        imageViewSuitcase1 = findViewById(R.id.imageViewSuitcase1);
        imageViewSuitcase2 = findViewById(R.id.imageViewSuitcase2);
        imageViewSuitcase3 = findViewById(R.id.imageViewSuitcase3);
        imageViewSuitcase4 = findViewById(R.id.imageViewSuitcase4);
        imageViewSuitcase5 = findViewById(R.id.imageViewSuitcase5);
        imageViewSuitcase6 = findViewById(R.id.imageViewSuitcase6);
        imageViewSuitcase7 = findViewById(R.id.imageViewSuitcase7);
        imageViewSuitcase8 = findViewById(R.id.imageViewSuitcase8);
        imageViewSuitcase9 = findViewById(R.id.imageViewSuitcase9);
        imageViewSuitcase10 = findViewById(R.id.imageViewSuitcase10);

        imageViewReward_1 = findViewById(R.id.imageViewReward_1);
        imageViewReward_10 = findViewById(R.id.imageViewReward_10);
        imageViewReward_50 = findViewById(R.id.imageViewReward_50);
        imageViewReward_100 = findViewById(R.id.imageViewReward_100);
        imageViewReward_300 = findViewById(R.id.imageViewReward_300);
        imageViewReward_1000 = findViewById(R.id.imageViewReward_1000);
        imageViewReward_10000 = findViewById(R.id.imageViewReward_10000);
        imageViewReward_50000 = findViewById(R.id.imageViewReward_50000);
        imageViewReward_100000 = findViewById(R.id.imageViewReward_100000);
        imageViewReward_500000 = findViewById(R.id.imageViewReward_500000);

        buttonDeal = findViewById(R.id.buttonDeal);
        buttonNoDeal = findViewById(R.id.buttonNoDeal);
        buttonReset = findViewById(R.id.buttonReset);

        buttonDeal.setVisibility(View.INVISIBLE);
        buttonNoDeal.setVisibility(View.INVISIBLE);
        textViewChooseCases.setText("Choose " + count + " cases");

        Arrays.fill(visited,0);

        initializeNumbersList();



        buttonDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewChooseCases.setText("You Won $" + offer);
                buttonDeal.setVisibility(View.INVISIBLE);
                buttonNoDeal.setVisibility(View.INVISIBLE);
            }
        });

        buttonNoDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonCount++;
                if (buttonCount == 1) {

                    count = 4;

                } else if (buttonCount == 2) {

                    count = 1;

                } else {
                    count = 4;
                }
                textViewChooseCases.setText("Choose " + count + " cases");
                buttonDeal.setVisibility(View.INVISIBLE);
                buttonNoDeal.setVisibility(View.INVISIBLE);
                // Reset clickability of suitcases for the new round
                resetSuitcaseClickability();
            }

            private void resetSuitcaseClickability() {
                imageViewSuitcase1.setClickable(true);
                imageViewSuitcase2.setClickable(true);
                imageViewSuitcase3.setClickable(true);
                imageViewSuitcase4.setClickable(true);
                imageViewSuitcase5.setClickable(true);
                imageViewSuitcase6.setClickable(true);
                imageViewSuitcase7.setClickable(true);
                imageViewSuitcase8.setClickable(true);
                imageViewSuitcase9.setClickable(true);
                imageViewSuitcase10.setClickable(true);
            }
        });





        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        imageViewSuitcase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[0] == 0) {
                    openSuitcase(imageViewSuitcase1);
                    imageViewSuitcase1.setClickable(false);
                }

                visited[0] = 1;

            }
        });
        imageViewSuitcase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[1] == 0) {
                    openSuitcase(imageViewSuitcase2);
                    imageViewSuitcase2.setClickable(false);
                }

                visited[1] = 1;

            }
        });
        imageViewSuitcase3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[2] == 0) {
                    openSuitcase(imageViewSuitcase3);
                    imageViewSuitcase3.setClickable(false);
                }

                visited[2] = 1;

            }
        });
        imageViewSuitcase4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[3] == 0) {
                    openSuitcase(imageViewSuitcase4);
                    imageViewSuitcase4.setClickable(false);
                }

                visited[3] = 1;

            }
        });
        imageViewSuitcase5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[4] == 0) {
                    openSuitcase(imageViewSuitcase5);
                    imageViewSuitcase5.setClickable(false);
                }

                visited[4]= 1;

            }
        });
        imageViewSuitcase6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[5] == 0) {
                    openSuitcase(imageViewSuitcase6);
                    imageViewSuitcase6.setClickable(false);
                }

                visited[5] =1;

            }
        });
        imageViewSuitcase7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[6] == 0) {
                    openSuitcase(imageViewSuitcase7);
                    imageViewSuitcase7.setClickable(false);
                }

                visited[6]= 1;

            }
        });
        imageViewSuitcase8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[7]==0) {
                    openSuitcase(imageViewSuitcase8);
                    imageViewSuitcase8.setClickable(false);
                }

                visited[7] =1;

            }
        });
        imageViewSuitcase9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[8] == 0) {
                    openSuitcase(imageViewSuitcase9);
                    imageViewSuitcase9.setClickable(false);
                }

                visited[8] = 1;

            }
        });
        imageViewSuitcase10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (visited[9] == 0) {
                    openSuitcase(imageViewSuitcase10);
                    imageViewSuitcase10.setClickable(false);
                }

                visited[9] =1;

            }
        });

    }



    private static void initializeNumbersList() {
        numbers.clear();
        numbers.add(1);
        numbers.add(10);
        numbers.add(50);
        numbers.add(100);
        numbers.add(300);
        numbers.add(1000);
        numbers.add(10000);
        numbers.add(50000);
        numbers.add(100000);
        numbers.add(500000);

        Collections.shuffle(numbers);
    }


    @SuppressLint("SetTextI18n")
    private void openSuitcase(ImageView clickedImageView) {
        StringBuilder sb = new StringBuilder();
        if (currentIndex < numbers.size()) {
            int amount = numbers.get(currentIndex);
            setNumbersToImageView(clickedImageView, amount);
            setReward(amount);
            currentIndex++;
            sum = sum + amount;
        }

        // For third round
        if (buttonCount == 2 && count == 1) {
            // Show the winning amount and end the game
            textViewChooseCases.setText("Congratulations! You won $" + (total-sum));
            buttonDeal.setVisibility(View.INVISIBLE);
            buttonNoDeal.setVisibility(View.INVISIBLE);
            disableAllSuitcases();
        } else {
            // if all rounds are done
            if (buttonCount < 3 || count > 1) {
                if (count > 1) {
                    --count;
                    if (buttonCount == 2) {
                        textViewChooseCases.setText("Select 1 case");
                    } else {
                        textViewChooseCases.setText("Choose " + count + " cases");
                    }
                } else {
                    // give offer or proceed
                    if (buttonCount == 2) {
                        buttonDeal.setVisibility(View.INVISIBLE);
                        buttonNoDeal.setVisibility(View.INVISIBLE);
                        sb.append("You won $");
                    } else {
                        buttonDeal.setVisibility(View.VISIBLE);
                        buttonNoDeal.setVisibility(View.VISIBLE);
                        imageViewSuitcase1.setClickable(false);
                        imageViewSuitcase2.setClickable(false);
                        imageViewSuitcase3.setClickable(false);
                        imageViewSuitcase4.setClickable(false);
                        imageViewSuitcase5.setClickable(false);
                        imageViewSuitcase6.setClickable(false);
                        imageViewSuitcase7.setClickable(false);
                        imageViewSuitcase8.setClickable(false);
                        imageViewSuitcase9.setClickable(false);
                        imageViewSuitcase10.setClickable(false);

                        sb.append("Bank Deal is $");
                    }

                    offer = ((total - sum) / (10 - currentIndex)) * (0.6);
                    textViewChooseCases.setText(sb.toString() + offer);
                }
            }
        }
    }



    private void disableAllSuitcases() {
        imageViewSuitcase1.setClickable(false);
        imageViewSuitcase2.setClickable(false);
        imageViewSuitcase3.setClickable(false);
        imageViewSuitcase4.setClickable(false);
        imageViewSuitcase5.setClickable(false);
        imageViewSuitcase6.setClickable(false);
        imageViewSuitcase7.setClickable(false);
        imageViewSuitcase8.setClickable(false);
        imageViewSuitcase9.setClickable(false);
        imageViewSuitcase10.setClickable(false);
    }

        private void resetGame() {
            initializeNumbersList();
            currentIndex = 0;
            count = 4;
            buttonCount = 0;
            sum = 0;
            offer = 0;
            Arrays.fill(visited,0);


            textViewChooseCases.setText("Choose " + count + " cases");
            buttonDeal.setVisibility(View.INVISIBLE);
            buttonNoDeal.setVisibility(View.INVISIBLE);
            imageViewSuitcase1.setClickable(true);
            imageViewSuitcase2.setClickable(true);
            imageViewSuitcase3.setClickable(true);
            imageViewSuitcase4.setClickable(true);
            imageViewSuitcase5.setClickable(true);
            imageViewSuitcase6.setClickable(true);
            imageViewSuitcase7.setClickable(true);
            imageViewSuitcase8.setClickable(true);
            imageViewSuitcase9.setClickable(true);
            imageViewSuitcase10.setClickable(true);


            imageViewSuitcase1.setImageResource(R.drawable.suitcase_position_1);
            imageViewSuitcase2.setImageResource(R.drawable.suitcase_position_2);
            imageViewSuitcase3.setImageResource(R.drawable.suitcase_position_3);
            imageViewSuitcase4.setImageResource(R.drawable.suitcase_position_4);
            imageViewSuitcase5.setImageResource(R.drawable.suitcase_position_5);
            imageViewSuitcase6.setImageResource(R.drawable.suitcase_position_6);
            imageViewSuitcase7.setImageResource(R.drawable.suitcase_position_7);
            imageViewSuitcase8.setImageResource(R.drawable.suitcase_position_8);
            imageViewSuitcase9.setImageResource(R.drawable.suitcase_position_9);
            imageViewSuitcase10.setImageResource(R.drawable.suitcase_position_10);

            imageViewReward_1.setImageResource(R.drawable.reward_1);
            imageViewReward_10.setImageResource(R.drawable.reward_10);
            imageViewReward_50.setImageResource(R.drawable.reward_50);
            imageViewReward_100.setImageResource(R.drawable.reward_100);
            imageViewReward_300.setImageResource(R.drawable.reward_300);
            imageViewReward_1000.setImageResource(R.drawable.reward_1000);
            imageViewReward_10000.setImageResource(R.drawable.reward_10000);
            imageViewReward_50000.setImageResource(R.drawable.reward_50000);
            imageViewReward_100000.setImageResource(R.drawable.reward_100000);
            imageViewReward_500000.setImageResource(R.drawable.reward_500000);
        }


    private void setReward(int amount) {
        switch (amount) {
            case 1 -> imageViewReward_1.setImageResource(R.drawable.reward_open_1);
            case 10 -> imageViewReward_10.setImageResource(R.drawable.reward_open_10);
            case 50 -> imageViewReward_50.setImageResource(R.drawable.reward_open_50);
            case 100 -> imageViewReward_100.setImageResource(R.drawable.reward_open_100);
            case 300 -> imageViewReward_300.setImageResource(R.drawable.reward_open_300);
            case 1000 -> imageViewReward_1000.setImageResource(R.drawable.reward_open_1000);
            case 10000 -> imageViewReward_10000.setImageResource(R.drawable.reward_open_10000);
            case 50000 -> imageViewReward_50000.setImageResource(R.drawable.reward_open_50000);
            case 100000 -> imageViewReward_100000.setImageResource(R.drawable.reward_open_100000);
            case 500000 -> imageViewReward_500000.setImageResource(R.drawable.reward_open_500000);
            default -> {}

        }
    }

    private void setNumbersToImageView(ImageView imageView, int amount) {

        switch (amount) {
            case 1 -> imageView.setImageResource(R.drawable.suitcase_open_1);
            case 10 -> imageView.setImageResource(R.drawable.suitcase_open_10);
            case 50 -> imageView.setImageResource(R.drawable.suitcase_open_50);
            case 100 -> imageView.setImageResource(R.drawable.suitcase_open_100);
            case 300 -> imageView.setImageResource(R.drawable.suitcase_open_300);
            case 1000 -> imageView.setImageResource(R.drawable.suitcase_open_1000);
            case 10000 -> imageView.setImageResource(R.drawable.suitcase_open_10000);
            case 50000 -> imageView.setImageResource(R.drawable.suitcase_open_50000);
            case 100000 -> imageView.setImageResource(R.drawable.suitcase_open_100000);
            case 500000 -> imageView.setImageResource(R.drawable.suitcase_open_500000);
            default -> {}

        }
    }
}