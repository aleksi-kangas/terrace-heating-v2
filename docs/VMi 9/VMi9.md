# Lämpöässä VMi 9 - Ground Source Heat Pump

## References

- VSi 6-14, VMi 6-17 ÄssäControl - Installation and Operation Manual

## Acronyms

- HDC = Heat Distribution Circuit
- UST = Upper Storage Tank
- LST = Lower Storage Tank

## Coil

> 0x01 (Read), 0x05 (Write Single), 0x15 (Write Multiple)

| Variable  | Coil (Discrete Output) |
|-----------|------------------------|
| Timers On | 134 (?)                |

## Holding Register

> 0x03 (Read), 0x06 (Write Single), 0x16 (Write Multiple)

| Variable                       | Holding Register  | Unit    |
|--------------------------------|-------------------|---------|
| Outdoor                        | 1                 | 10 x °C |
| Hot Gas 1                      | 2                 | 10 x °C |
| Hot Gas 2                      | 3                 | 10 x °C |
| HDC 1                          | 5                 | 10 x °C |
| HDC 2                          | 6                 | 10 x °C |
| ------------------------------ | ----------------- | ------- |
| LST                            | 17                | 10 x °C |
| UST                            | 18                | 10 x °C |
| ------------------------------ | ----------------- | ------- |
| LST Timer Monday Δ             | 36                | °C      |
| LST Timer Tuesday Δ            | 37                | °C      |
| LST Timer Wednesday Δ          | 38                | °C      |
| LST Timer Thursday Δ           | 39                | °C      |
| LST Timer Friday Δ             | 41                | °C      |
| LST Timer Saturday Δ           | 42                | °C      |
| LST Timer Sunday Δ             | 43                | °C      |
| ------------------------------ | ----------------- | ------- |
| LST Minimum                    | 71                | °C      |
| LST Maximum                    | 72                | °C      |
| ------------------------------ | ----------------- | ------- |
| Indoor                         | 74                | 10 x °C |
| LST Minimum Adjusted           | 75                | °C      |
| LST Maximum Adjusted           | 76                | °C      |
| UST Minimum                    | 77                | °C      |
| UST Maximum                    | 78                | °C      |
| UST Minimum Adjusted           | 79                | °C      |
| UST Maximum Adjusted           | 80                | °C      |
| ------------------------------ | ----------------- | ------- |
| Ground Circuit Out             | 98                | 10 x °C |
| Ground Circuit In              | 99                | 10 x °C |
| ------------------------------ | ----------------- | ------- |
| HDC 3 Timer Tuesday Δ          | 106               | °C      |
| HDC 3 Timer Monday Δ           | 107               | °C      |
| HDC 3 Timer Saturday Δ         | 108               | °C      |
| HDC 3 Timer Sunday Δ           | 109               | °C      |
| HDC 3 Timer Wednesday Δ        | 110               | °C      |
| HDC 3 Timer Thursday Δ         | 111               | °C      |
| HDC 3 Timer Friday Δ           | 112               | °C      |
| ------------------------------ | ----------------- | ------- |
| HDC 3                          | 117               | 10 x °C |
| ------------------------------ | ----------------- | ------- |
| LST Timer Monday Start Hour    | 5014              | h       |
| LST Timer Tuesday Start Hour   | 5015              | h       |
| LST Timer Wednesday Start Hour | 5016              | h       |
| LST Timer Thursday Start Hour  | 5017              | h       |
| LST Timer Friday Start Hour    | 5018              | h       |
| LST Timer Saturday Start Hour  | 5019              | h       |
| LST Timer Sunday Start Hour    | 5020              | h       |
| LST Timer Monday End Hour      | 5021              | h       |
| LST Timer Tuesday End Hour     | 5022              | h       |
| LST Timer Wednesday End Hour   | 5023              | h       |
| LST Timer Thursday End Hour    | 5024              | h       |
| LST Timer Friday End Hour      | 5025              | h       |
| LST Timer Saturday End Hour    | 5026              | h       |
| LST Timer Sunday End Hour      | 5027              | h       |
| ------------------------------ | ----------------- | ------- |
| HDC Count                      | 5100              |         |
| ------------------------------ | ----------------- | ------- |
| Compressor Active              | 5158              |         |
| ------------------------------ | ----------------- | ------- |
| HDC 3 Tuesday Start Hour       | 5211              | h       |
| HDC 3 Tuesday End Hour         | 5212              | h       |
| HDC 3 Monday End Hour          | 5213              | h       |
| HDC 3 Monday Start Hour        | 5214              | h       |
| HDC 3 Thursday End Hour        | 5215              | h       |
| HDC 3 Saturday Start Hour      | 5216              | h       |
| HDC 3 Saturday End Hour        | 5217              | h       |
| HDC 3 Sunday Start Hour        | 5218              | h       |
| HDC 3 Sunday End Hour          | 5219              | h       |
| HDC 3 Wednesday Start Hour     | 5220              | h       |
| HDC 3 Wednesday End Hour       | 5221              | h       |
| HDC 3 Thursday Start Hour      | 5222              | h       |
| HDC 3 Friday Start Hour        | 5223              | h       |
| HDC 3 Friday End Hour          | 5224              | h       |
| ------------------------------ | ----------------- | ------- |

### TODO

- HDC 1 Timer
- HDC 2 Timer
- UST Timer

