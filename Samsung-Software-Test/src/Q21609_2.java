import java.io.*;
import java.util.*;

public class Q21609_2 {

    static int N, M, CNT, answer = 0;
    static int[][] map = new int[20][20];
    static int[][] check = new int[20][20];
    static int[][][] info = new int[20][20][3];
    static int[][] delta = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while (true) {
            if (play() == -1) {
                break;
            }
        }

        System.out.print(answer);
    }

    static int play() {

        init();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] > 0 && check[i][j] == 0) {
                    check[i][j] = CNT;
                    info[i][j] = dfs(i, j, map[i][j], 0);
                    CNT++;
                }
            }
        }

        int[] p = getBlock();

        if (p[0] == -1 || info[p[0]][p[1]][1] < 2) {
            return -1;
        }

        dfs(p[0], p[1], map[p[0]][p[1]], 1);
        answer += info[p[0]][p[1]][1] * info[p[0]][p[1]][1];

        gravity();
        rotate();
        gravity();

        return 0;
    }

    static void init() {

        CNT = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                check[i][j] = 0;
                info[i][j][0] = info[i][j][1] = info[i][j][2] = 0;
            }
        }
    }

    static int[] dfs(int r, int c, int num, int flag) {

        int size = 1, nZero = 0;

        if (map[r][c] == 0) {
            nZero = 1;
        }

        for (int i = 0; i < 4; i++) {

            int nr = r + delta[i][0];
            int nc = c + delta[i][1];

            if (nr < 0 || nc < 0 || nr >= N || nc >= N) {
                continue;
            }

            if (map[nr][nc] < 0 || check[nr][nc] == CNT) {
                continue;
            }

            if (map[nr][nc] == 0 || map[nr][nc] == num) {
                check[nr][nc] = CNT;
                int[] res = dfs(nr, nc, num, flag);
                size += res[1];
                nZero += res[2];
            }
        }

        if (flag == 1) {
            map[r][c] = -2;
        }

        return new int[]{1, size, nZero};
    }

    static int[] getBlock() {

        int r = -1, c = -1, maxSize = -1, maxZeroSize = -1;
        for (int i = N - 1; i >= 0; i--) {
            for (int j = N - 1; j >= 0; j--) {
                if (info[i][j][0] == 1) {
                    if (info[i][j][1] > maxSize) {
                        r = i;
                        c = j;
                        maxSize = info[i][j][1];
                        maxZeroSize = info[i][j][2];
                    } else if (info[i][j][1] == maxSize && info[i][j][2] > maxZeroSize) {
                        r = i;
                        c = j;
                        maxZeroSize = info[i][j][2];
                    }
                }
            }
        }

        return new int[]{r, c};
    }

    static void rotate() {

        int[][] tmp = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tmp[N - 1 - j][i] = map[i][j];
            }
        }
        map = tmp;
    }

    static void gravity() {

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] < 0) {
                    continue;
                }
                int r = i, c = j;
                for (int k = 0; k < N; k++) {
                    r++;
                    if ((r < 0 || r >= N) || map[r][c] != -2) {
                        break;
                    }
                }
                map[r - 1][c] = map[i][j];
                if (r - 1 > i) {
                    map[i][j] = -2;
                }
            }
        }
    }

    static void print(int[][] arr) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.printf("%3d", arr[i][j]);
            } System.out.println();
        } System.out.println();
    }
}
