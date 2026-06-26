# Flow Chart Diagram without Details

```mermaid
flowchart TD

A[Main Screen]
B[Vacation List]
C[Vacation Details]
D[Excursion Details]
E[Room Database]

A <--> B
B <--> C
C <--> D
C <--> E
D <--> E

```