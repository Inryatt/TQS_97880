package tqs.hw.covidtracker.data;

public class CountryDTO {
    private Long updated; // Correct as of:
    private String country;
    private Integer cases;
    private Integer todayCases;
    private Integer deaths;
    private Integer todayDeaths;
    private Integer recovered;
    private Integer todayRecovered;
    private Integer active;
    private Integer critical;
    private Integer casesPerOneMillion;
    private Integer deathsPerOneMillion;
    private Integer tests;
    private Integer testsPerOneMillion;
    private Double activePerOneMillion;
    private Double recoveredPerOneMillion;
    private Double criticalPerOneMillion;
    private Integer population;
    private String continent;
    private Integer oneCasePerPeople;
    private Integer oneDeathPerPeople;

    public CountryDTO(){}

    public CountryDTO(Long updated, String name, Integer cases, Integer todayCases, Integer deaths, Integer todayDeaths, Integer recovered, Integer todayRecovered, Integer active, Integer critical, Integer casesPerOneMillion, Integer deathsPerOneMillion, Integer tests, Integer testsPerOneMillion, Double activePerOneMillion, Double recoveredOneMillion, Double criticalOneMillion, Integer population, String continent, Integer oneCasePerPeople, Integer oneDeathPerPeople) {
        this.updated = System.currentTimeMillis();
        this.country = name;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
        this.active = active;
        this.critical = critical;
        this.casesPerOneMillion = casesPerOneMillion;
        this.deathsPerOneMillion = deathsPerOneMillion;
        this.tests = tests;
        this.testsPerOneMillion = testsPerOneMillion;
        this.activePerOneMillion = activePerOneMillion;
        this.recoveredPerOneMillion = recoveredOneMillion;
        this.criticalPerOneMillion = criticalOneMillion;
        this.population = population;
        this.continent = continent;
        this.oneCasePerPeople = oneCasePerPeople;
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getName() {
        return country;
    }

    public void setName(String name) {
        this.country = name;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(Integer todayCases) {
        this.todayCases = todayCases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(Integer todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(Integer todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(Integer casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public Integer getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(Integer deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public Integer getTests() {
        return tests;
    }

    public void setTests(Integer tests) {
        this.tests = tests;
    }

    public Integer getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(Integer testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public Double getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(Double activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public Double getRecoveredOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredOneMillion(Double recoveredOneMillion) {
        this.recoveredPerOneMillion = recoveredOneMillion;
    }

    public Double getCriticalOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalOneMillion(Double criticalOneMillion) {
        this.criticalPerOneMillion = criticalOneMillion;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Integer getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(Integer oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public Integer getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(Integer oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }
}
