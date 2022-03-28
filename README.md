# ComposeUI
ComposeUI 저장소 결과물
![image](https://user-images.githubusercontent.com/70741953/159221477-e45a8974-8167-40de-9093-57ebfe837b72.png)

### BoxWithConstraint

기존 Compose의 Box와 유사하지만 BoxWithConstraintScope를 사용할 수 있게됨 이를 이용하여 Box의 크기를 비교하거나 크기에따른 색깔을 변경하는것이 가능함.

```kotlin
BoxWithConstraints(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
    ) {
        if(this.minWidth > 200.dp){
            Text(text = "이것은 큰 상자이다")
        }
        else Text(text = "이것은 작은 상자이다")
    }
```

위 예시처럼 `minWidth`를 기준으로 크기를 비교하는것과 `maxWidth`, `minHeight`, `maxHeight`로도 비교할 수 있다.
![Untitled](https://user-images.githubusercontent.com/70741953/160308059-750f1633-7e9b-4c21-8508-5dbe3ac8a7a7.png)
