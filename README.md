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


### Compose Text

`buildAnnotatedString`을 이용하면 글자중 단어나 한글자씩 색깔이나 크기등을 조절할 수 있다.
```kotlin
Text(
            buildAnnotatedString {
                withStyle(
                    style = ParagraphStyle(
                        lineHeight = 30.sp
                    )
                ){
                    withStyle(
                        style = SpanStyle(
                            background = Color.Yellow
                        )
                    ){
                        append("과실이 싹이 주며,")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Blue,
                                fontSize = 30.sp
                            )
                        )
                        {
                            append("청춘")
                        }
                        append("이것이다. 소리다. 이것은 이상을 그러므로 소금이라" +
                                "그림자는 원대하고, 유소년에게서 과실이 뿐이다")
                    }
                }
            }
        )
```
![image](https://user-images.githubusercontent.com/70741953/160310914-b7111fc3-ca5b-45d3-abc0-f8f214146516.png)
