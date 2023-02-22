

-   프로젝트 구조를 Application, Domain, Service, Infrastructure 로 나누었고, 도메인 관련 비지니스 로직은 모두 domain.service 에 놓아, Application에는 도메인간 간단한 로직이 들어가도록 구성하였습니다.


-   복잡한 도메인을 “관리하기 쉬운 단위로 만들기 위해” `애그리거트` 중심으로 도메인당 프로젝트 패키지 별로 나누었으며, 도메인간의 요소파악을 쉽게 만들었습니다.


-   DIP를 통해 구현 기술(Infrastructure)을 Domain에 있는 Repository 인터페이스를 통해 하위 기능을 추상화여 구현 기술에 변경이 있어도 Domain이 영향을 받지 않도록하였습니다