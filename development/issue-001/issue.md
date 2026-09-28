# 凝集度と結合度についての教材


## min-cohesion

凝集度最低のサンプルプログラムを作ろうとしている。

### java/com/example/study/Main.java

- １ファイル、mainメソッドのみで、HTTPサーバーを起動し、TODOアプリのAPIを実現する。
- 実行は
    ```shell
    gradlew run
    ```
    で起動すると、`http://localhost:8080/` で待ち受ける。

    ※ 10080番ポートはChromeの「安全でないポート」ブロックリストに含まれており`ERR_UNSAFE_PORT`となるため8080番に変更。
- ~~他のライブラリを必要としない。JDKの標準ライブラリのみで実現する。~~
  → JDK標準にはJSONの(デ)シリアライズ機能が無いため、Jacksonを利用する方針に変更。
- データはメモリ上に保持する。

## TODOアプリのAPI仕様

- GET /  でTODOリストを取得する。
- POST / でTODOリストを追加する。
