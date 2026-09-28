# Todo API 仕様

## 概要

- インメモリで動作するシンプルなTodo管理APIサーバー。
- ベースURL: `http://localhost:8080`
- データはプロセスのメモリ上にのみ保持され、サーバー再起動で初期状態（後述）にリセットされる。
- リクエスト/レスポンスのJSON処理には Jackson を使用する。

## エンドポイント一覧

| メソッド | パス      | 概要                     |
|----------|-----------|--------------------------|
| GET      | `/`       | ヘルプメッセージの表示   |
| GET      | `/todos`  | Todo一覧の取得           |
| POST     | `/todos`  | Todoの追加               |

---

## GET /

ルートアクセス時にヘルプを表示する。

### レスポンス

- ステータス: `200 OK`
- `Content-Type: text/plain; charset=utf-8`

```
GET  /todos  Todoの一覧を取得する
POST /todos  Todoを追加する
```

---

## GET /todos

登録されているTodoの一覧を取得する。

### レスポンス

- ステータス: `200 OK`
- `Content-Type: application/json; charset=utf-8`
- ボディ: Todoオブジェクトの配列

```json
[
  { "id": 1, "title": "犬の散歩" },
  { "id": 2, "title": "夕飯の買い物" }
]
```

---

## POST /todos

Todoを1件追加する。

### リクエスト

- `Content-Type: application/json`
- ボディ:

```json
{ "title": "牛乳を買う" }
```

| フィールド | 型     | 必須 | 説明          |
|------------|--------|------|---------------|
| `title`    | string | ○    | Todoのタイトル |

### レスポンス

- ステータス: `201 Created`
- `Content-Type: application/json; charset=utf-8`
- ボディ: 追加されたTodoオブジェクト（サーバーが採番した `id` を含む）

```json
{ "id": 3, "title": "牛乳を買う" }
```

### 採番規則

- `id` はサーバー側で自動採番される連番（初期値3から開始、初期データが1, 2を使用しているため）。

---

## データモデル

### Todo

| フィールド | 型     | 説明               |
|------------|--------|--------------------|
| `id`       | number | 一意な識別子（自動採番） |
| `title`    | string | タイトル           |

### 初期データ

サーバー起動時、以下の2件が登録された状態になる。

```json
[
  { "id": 1, "title": "犬の散歩" },
  { "id": 2, "title": "夕飯の買い物" }
]
```

---

## エラー・未定義メソッド

`/todos` に対して GET・POST 以外のメソッドでアクセスした場合:

- ステータス: `405 Method Not Allowed`
- ボディ: なし

上記一覧にないパス（例: `/foo`, `/todos/1`）にアクセスした場合:

- ステータス: `404 Not Found`
- ボディ: なし

---

## 起動方法

```shell
gradlew run
```

`http://localhost:8080/` で待ち受ける。

※ ポート10080はChromeの「安全でないポート」ブロックリストに含まれ `ERR_UNSAFE_PORT` になるため、8080を使用している。
