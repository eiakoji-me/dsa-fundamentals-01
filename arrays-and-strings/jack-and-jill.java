import java.util.*;

class JackAndJill {
    public static int playTheGame(int[] numbers){
        int incrementOps = 0;
        int maxDoubleOps = 0;

        for(int number : numbers){
            int countDoubleOps = 0;
            while(number > 0){
                // Min ops for even numbers is doubling
                if(number % 2 == 0){
                    countDoubleOps++;
                    number /= 2;
                } else { // odds can only be attained by incs
                    number--;
                    incrementOps++;
                }
            }
            maxDoubleOps = Math.max(maxDoubleOps, countDoubleOps);
        }

        return incrementOps + maxDoubleOps;
    }

    public static void main(String[] args){
        List<int[]> inputs = List.of(
                    new int[]{2,3},
                    new int[]{16,16,16},
                    new int[]{}
                );
        //inputs.stream().map(n -> playTheGame(n)).forEach(System.out::println);
        inputs.forEach(arr -> {
            System.out.printf("%s -> %d%n", Arrays.toString(arr),playTheGame(arr));
        });
    }
}
