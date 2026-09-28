# 凝集度のサンプル

## IntelliJでのセットアップ手順

### 1. プロジェクトSDKを設定

ローカルに存在するJDK21をセットする

### 2. build.gradleを右クリックして「Gradleプロジェクトのリンク」する

- workshop/build.gradle
- answer/build.gradle

### 3. frontend.imlを右クリックして「インポート」する

- frontend/frontend.iml

## ディレクトリの説明

### frontend

APIのテスト用UI。IntelliJの起動構成「frontend」で実行します。

### workshop

JavaのHTTPサーバーアプリケーション。IntelliJの起動構成「workshop [run]」で実行します。

### answer

凝集度を高めた結果、こうなるというサンプル。
