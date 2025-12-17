export interface MarketIntelligenceResponse {
  id: string;
  customerId: string;
  businessNiche: string | null;
  digitalPresenceScore: number | null;
  estimatedMaturity: string | null;
  recommendedApproach: string | null;
  socialMediaPlatform: string | null;
  socialMediaFollowers: number | null;
  hasGoogleMapsPresence: boolean | null;
}

export interface OpportunityAnalysisResponse {
  id: string;
  customerId: string;
  potentialScore: number;
  monthlyLoss: number;
  shadowLimit: number;
  identifiedRevenue: number;
  recommendation: string;
  marketIntelligence: MarketIntelligenceResponse | null;
}

export interface ApiError {
  message: string;
  status: number;
}

