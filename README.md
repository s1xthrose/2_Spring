# 2_Spring

# Как пользоваться компасом:

1. Отправляем POST-запрос на адрес http://*/setSides:

{
    "North": "",
    "Northeast": "",
    "East": "",
    "Southeast": "",
    "South": "",
    "Southwest": "",
    "West": "",
    "Northwest": ""
}

2. После этого отправляем GET-запрос на адрес http://*/getSide:

{
    "degree": 
}

3. Получаем ответ :3
